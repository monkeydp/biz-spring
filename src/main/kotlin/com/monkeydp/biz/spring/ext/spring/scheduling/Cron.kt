package com.monkeydp.biz.spring.ext.spring.scheduling

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author iPotato-Work
 * @date 2020/8/10
 */
fun Date.toCronExp(): String =
        SimpleDateFormat("ss mm HH dd MM ?")
                .format(this)

fun TaskScheduler.scheduleCronTask(run: () -> Unit, cronExp: String) {
    schedule(object : Runnable {
        override fun run() {
            run()
        }
    }, CronTrigger(cronExp))
}
