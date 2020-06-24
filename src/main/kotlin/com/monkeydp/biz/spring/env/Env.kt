package com.monkeydp.biz.spring.env

/**
 * gt: greater than 大于
 * gte: greater than or equal 大于等于
 * lt: less than 小于
 * lte: less than or equal 小于等于
 *
 * @author iPotato
 * @date 2020/4/27
 */
object Env {
    const val LOCAL = "local"
    const val NOT_LOCAL = "!$LOCAL"

    const val DEV = "dev"
    const val NOT_DEV = "!$DEV"

    const val TEST = "test"
    const val NOT_TEST = "!$TEST"

    const val PROD = "prod"
    const val NOT_PROD = "!$PROD"

    const val LTE_DEV = "$LOCAL | $DEV"
    const val LTE_TEST = "$LOCAL | $DEV | $TEST"
}