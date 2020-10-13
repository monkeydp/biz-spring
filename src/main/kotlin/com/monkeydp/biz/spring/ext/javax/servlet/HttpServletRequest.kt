package com.monkeydp.biz.spring.ext.javax.servlet

import org.springframework.http.HttpMethod
import javax.servlet.http.HttpServletRequest

/**
 * @author iPotato-Work
 * @date 2020/10/13
 */
fun HttpServletRequest.isMethod(method: HttpMethod) =
        this.method == method.name
