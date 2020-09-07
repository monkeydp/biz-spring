package com.monkeydp.biz.spring.auth

import com.monkeydp.tools.exception.inner.InnerException

/**
 * @author iPotato-Work
 * @date 2020/9/7
 */
class AuthFailEx(cause: Throwable? = null) : InnerException(cause = cause)

class AccountNotExistEx : InnerException()

class PwdIncorrectEx : InnerException()
