package com.monkeydp.biz.spring.result

/**
 * @author iPotato-Work
 * @date 2020/5/15
 */
enum class CommonErrorInfo(
        override val code: String,
        override val msgPattern: String
) : ResultInfo {
    ARGUMENT_ILLEGAL("300", "参数不合法"),
    DATA_NOT_EXIST("301", "`{object}`不存在"),
    DATA_ALREADY_EXIST("302", "`{object}`已存在"),

    SMS_BUSY("311", "短信服务繁忙"),

    INNER_ERROR("500", "内部异常");
}
