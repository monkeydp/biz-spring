package com.monkeydp.biz.spring.ext.spring.scheduling

import com.monkeydp.tools.module.cron.Cron
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger

/**
 * @author iPotato-Work
 * @date 2020/8/10
 */
fun TaskScheduler.scheduleCronTask(cronExp: CharSequence, task: () -> Unit) =
        schedule(object : Runnable {
            override fun run() {
                task()
            }
        }, CronTrigger(cronExp.toString()))

fun TaskScheduler.scheduleCronTask(cron: Cron, task: () -> Unit) =
        scheduleCronTask(cron.expression, task)
