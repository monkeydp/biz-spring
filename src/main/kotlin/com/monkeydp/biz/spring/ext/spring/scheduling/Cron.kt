package com.monkeydp.biz.spring.ext.spring.scheduling

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author iPotato-Work
 * @date 2020/8/10
 */
fun Date.toCronExp(): String =
        SimpleDateFormat("ss mm HH dd MM ?")
                .format(this)
