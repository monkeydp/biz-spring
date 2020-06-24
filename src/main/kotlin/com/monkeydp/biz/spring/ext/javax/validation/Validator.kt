package com.monkeydp.biz.spring.ext.javax.validation

import com.monkeydp.tools.ext.javax.validation.SimpleBeanRule
import com.monkeydp.tools.ext.javax.validation.SimpleConstraintRule
import com.monkeydp.tools.ext.javax.validation.getSimpleBeanRuleMap
import java.lang.reflect.Parameter
import javax.validation.Validator

/**
 * @author iPotato-Work
 * @date 2020/6/11
 */
fun Validator.getMyBeanRuleMap(parameters: Iterable<Parameter>): Map<String, Map<String, Map<String, MyConstraintRule>>> =
        getSimpleBeanRuleMap(parameters)
                .map { it.key.parameterizedType.typeName to it.value.toMyConstraintRuleMap() }
                .toMap()

private fun SimpleBeanRule.toMyConstraintRuleMap() =
        propRules.map { propRule ->
            propRule.propName to
                    propRule.cstrRules
                            .map { it.cstrClassname to MyConstraintRule(it) }
                            .toMap()
        }.toMap()

class MyConstraintRule(
        val cstrAttrs: Map<String, Any>,
        val msgTmpl: String
) {
    constructor(rule: SimpleConstraintRule) : this(
            cstrAttrs = rule.cstrAttrs,
            msgTmpl = rule.msgTmpl
    )
}