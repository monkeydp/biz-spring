package com.monkeydp.biz.spring.result

import org.kodein.di.simpleErasedName
import org.springframework.validation.FieldError
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaType

interface ValidError {
    val message: String
    val cstrName: String
    val objName: String
    val propName: String
    val illegalValue: Any

    companion object {
        operator fun invoke(
                message: String,
                cstrName: String,
                objName: String,
                propName: String,
                illegalValue: Any
        ): ValidError =
                object : BaseValidError() {
                    override val message = message
                    override val cstrName = cstrName
                    override val objName = objName
                    override val propName = propName
                    override val illegalValue = illegalValue
                }

        operator fun invoke(fieldError: FieldError) =
                fieldError.run {
                    ValidError(
                            message = defaultMessage ?: "<unknown>",
                            objName = objectName,
                            propName = field,
                            cstrName = code ?: "<unknown>",
                            illegalValue = rejectedValue?.toString() ?: "<unknown>"
                    )
                }
    }
}

abstract class BaseValidError : ValidError

class UniqueError(
        override val objName: String,
        override val propName: String,
        override val illegalValue: Any
) : BaseValidError() {
    override val message = "唯一数据已存在"
    override val cstrName = "Unique"

    constructor(prop: KProperty1<*, *>, illegalValue: Any) :
            this(
                    objName = prop.returnType.javaType.simpleErasedName(),
                    propName = prop.name,
                    illegalValue = illegalValue
            )
}

fun Collection<ValidError>.groupByIllegalValue() =
        groupBy { it.illegalValue }
