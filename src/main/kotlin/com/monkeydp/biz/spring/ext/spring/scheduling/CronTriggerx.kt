package com.monkeydp.biz.spring.ext.spring.scheduling

import com.monkeydp.biz.spring.ext.spring.scheduling.CronTriggerx.Options
import com.monkeydp.biz.spring.ext.spring.scheduling.Times.ONCE
import com.monkeydp.biz.spring.ext.spring.scheduling.Times.REPEATEDLY
import com.monkeydp.tools.ext.java.plus
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.TriggerContext
import org.springframework.scheduling.support.CronSequenceGenerator
import org.springframework.scheduling.support.CronTrigger
import java.util.*

/**
 * @author iPotato-Work
 * @date 2021/1/27
 */
interface CronTriggerx : Trigger {

    companion object {
        operator fun invoke(expression: String, options: Options): CronTriggerx =
                CronTriggerxImpl(expression, options)

        operator fun invoke(expression: String, options: (Options.() -> Unit)? = null): CronTriggerx =
                this(expression, Options(options))
    }

    class Options(
            /**
             * 偏移时间执行
             *
             * 正数表示延后, 负数表示提前
             */
            var offsetTime: Int = 0,

            var timeunit: Int = Calendar.SECOND,

            var times: Times = REPEATEDLY,
    ) {
        companion object {
            operator fun invoke(init: (Options.() -> Unit)? = null) =
                    Options()
                            .apply { init?.invoke(this) }
        }
    }
}

private class CronTriggerxImpl(
        expression: String,
        private val options: Options,
) : CronTriggerx, Trigger by CronTrigger(expression) {

    private val sequenceGenerator = CronSequenceGenerator(expression)

    override fun nextExecutionTime(triggerContext: TriggerContext) =
            options.run {
                var date = triggerContext.lastCompletionTime()
                if (times == ONCE && date != null) return@run null
                when {
                    date == null -> date = Date()
                    else -> {
                        val scheduled = triggerContext.lastScheduledExecutionTime()
                        if (scheduled != null && date.before(scheduled))
                            date = scheduled
                    }
                }
                date = date.plus(-offsetTime, timeunit)
                sequenceGenerator.next(date).plus(offsetTime, timeunit)
            }
}

enum class Times {
    ONCE, REPEATEDLY
}
