package com.monkeydp.biz.spring.auth

import com.monkeydp.tools.exception.inner.InnerEx

/**
 * @author iPotato-Work
 * @date 2020/9/7
 */
class AuthFailEx(cause: Throwable? = null) : InnerEx(message = "认证失败", cause = cause)

class AccountNotExistEx : InnerEx(message = "账号不存在")

class PwdIncorrectEx : InnerEx(message = "密码错误")
