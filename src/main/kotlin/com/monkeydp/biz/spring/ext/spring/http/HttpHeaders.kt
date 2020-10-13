package com.monkeydp.biz.spring.ext.spring.http

import org.springframework.http.HttpHeaders

/**
 * @author iPotato-Work
 * @date 2020/10/13
 */
fun httpHeadersOf(vararg pairs: Pair<String, String>) =
        HttpHeaders()
                .apply {
                    pairs.forEach {
                        set(it.first, it.second)
                    }
                }
