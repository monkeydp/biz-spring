package com.monkeydp.biz.spring.auth

import com.monkeydp.tools.exception.inner.InnerException

/**
 * @author iPotato-Work
 * @date 2020/9/7
 */
class AuthFailEx(cause: Throwable? = null) : InnerException(message = "认证失败", cause = cause)

class AccountNotExistEx : InnerException(message = "账号不存在")

class PwdIncorrectEx : InnerException(message = "密码错误")
