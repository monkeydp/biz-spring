package com.monkeydp.biz.spring.result

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
enum class CommonInfo(
        override val code: Int,
        override val msgPattern: String
) : ResultInfo<CommonInfo> {
    ARGUMENT_ILLEGAL(300, "参数不合法"),
    DATA_NOT_FOUND(301, "`{object}`未找到"),

    INNER_ERROR(500, "内部异常");
}