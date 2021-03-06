package com.monkeydp.biz.spring.ext.spring.scheduling

import org.springframework.scheduling.TaskScheduler

/**
 * @author iPotato-Work
 * @date 2020/8/10
 */
fun TaskScheduler.scheduleCronTask(
        cronExp: CharSequence,
        options: (CronTriggerx.Options.() -> Unit)? = null,
        task: () -> Unit,
) =
        scheduleCronTask(CronTriggerx(cronExp.toString(), options), task)

fun TaskScheduler.scheduleCronTask(
        trigger: CronTriggerx,
        task: () -> Unit,
) =
        schedule(object : Runnable {
            override fun run() {
                task()
            }
        }, trigger)
