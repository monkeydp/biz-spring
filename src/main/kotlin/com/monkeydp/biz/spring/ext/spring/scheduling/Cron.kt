package com.monkeydp.biz.spring.ext.spring.scheduling

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author iPotato-Work
 * @date 2020/8/10
 */
val Date.cronExp: String
    get() =
        SimpleDateFormat("ss mm HH dd MM ?")
                .format(this)

fun TaskScheduler.scheduleCronTask(cronExp: String, task: () -> Unit) =
        schedule(object : Runnable {
            override fun run() {
                task()
            }
        }, CronTrigger(cronExp))
