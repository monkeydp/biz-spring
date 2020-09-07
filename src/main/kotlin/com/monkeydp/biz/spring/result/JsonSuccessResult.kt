package com.monkeydp.biz.spring.result

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.convertValue
import com.monkeydp.biz.spring.config.kodein
import com.monkeydp.biz.spring.crud.Paging
import com.monkeydp.biz.spring.crud.Table
import com.monkeydp.tools.ext.jackson.JsonFlatten
import com.monkeydp.tools.ext.jackson.JsonFlattener
import com.monkeydp.tools.ext.kotlin.findAnnotOrNull
import org.kodein.di.generic.instance
import org.springframework.core.MethodParameter

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
interface JsonSuccessResult<T> : SuccessResult<T> {
    fun toObjectNode(): ObjectNode
    fun ObjectNode.assignColumns(): ObjectNode
    fun ObjectNode.flattenData(): ObjectNode

    companion object {
        operator fun <T> invoke(data: T, returnType: MethodParameter): JsonSuccessResult<T> =
                JsonSuccessResultImpl(data, returnType)
    }
}

private class JsonSuccessResultImpl<T>(
        @JsonUnwrapped
        override val data: T,
        @JsonIgnore
        private val returnType: MethodParameter
) : JsonSuccessResult<T>, AbstractSuccessResult<T>(data) {

    private val _objectMapper by kodein.instance<ObjectMapper>()
    private val objectMapper = _objectMapper.copy()

    private val jsonView = returnType.getMethodAnnotation(JsonView::class.java)

    override fun toObjectNode(): ObjectNode {
        if (data == null || jsonView == null) {
            objectMapper.getSerializationConfig()
                    .withView(null)
                    .let(objectMapper::setConfig)
            return objectMapper.convertValue(this)
        }

        require(jsonView.value.size == 1)
        { "@JsonView only supported for response body advice with exactly 1 class argument: $returnType" }

        objectMapper.getSerializationConfig()
                .withView(jsonView.value[0].java)
                .let(objectMapper::setConfig)

        return objectMapper.convertValue(this)
    }

    override fun ObjectNode.assignColumns(): ObjectNode {
        when (data) {
            is Table<*> -> assignPagingColumns()
        }
        return this
    }

    private fun ObjectNode.assignPagingColumns(): ObjectNode {
        if (data !is Table<*>) return this
        val content = get(data::content.name)
        val columns = if (content.isEmpty) 0 else content.first().size()
        set<IntNode>(data::columns.name, IntNode(columns))
        return this
    }

    override fun ObjectNode.flattenData(): ObjectNode =
            when (data) {
                is Paging<*> -> flattenPagingData()
                is Table<*> -> flattenTableData()
                else -> this
            }

    private fun ObjectNode.flattenPagingData(): ObjectNode {
        if (data !is Paging<*>) return this
        val jsonFlatten: JsonFlatten? = data::content.findAnnotOrNull<JsonFlatten>()
        return JsonFlattener.flattenData(this, data::content.name, jsonFlatten)
    }

    private fun ObjectNode.flattenTableData(): ObjectNode {
        if (data !is Table<*>) return this
        val jsonFlatten: JsonFlatten? = data::content.findAnnotOrNull<JsonFlatten>()
        return JsonFlattener.flattenData(this, data::content.name, jsonFlatten)
    }
}
