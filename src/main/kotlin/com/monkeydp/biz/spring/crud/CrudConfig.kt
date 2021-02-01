package com.monkeydp.biz.spring.crud

/**
 * @author iPotato-Work
 * @date 2021/1/20
 */
interface SaveOptions<E> {
    var checkDuplicate: E.() -> Unit

    companion object {
        operator fun <E> invoke(another: ((SaveOptions<E>) -> Unit)? = null) =
                object : SaveOptions<E> {
                    override var checkDuplicate: E.() -> Unit = {}

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface SaveAllOptions<E> {
    var checkDuplicate: Iterable<E>.() -> Unit

    companion object {
        operator fun <E> invoke(another: ((SaveAllOptions<E>) -> Unit)? = null) =
                object : SaveAllOptions<E> {
                    override var checkDuplicate: Iterable<E>.() -> Unit = {}

                    init {
                        another?.invoke(this)
                    }
                }
    }
}


interface CreateOptions<E> : SaveOptions<E> {

    var enableCheckExistNo: Boolean

    companion object {
        operator fun <E> invoke(another: ((CreateOptions<E>) -> Unit)? = null) =
                object : CreateOptions<E> {
                    override var checkDuplicate: E.() -> Unit = {}
                    override var enableCheckExistNo = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface CreateAllOptions<E> : SaveAllOptions<E> {

    var enableCheckExistNo: Boolean

    companion object {
        operator fun <E> invoke(another: ((CreateAllOptions<E>) -> Unit)? = null) =
                object : CreateAllOptions<E> {
                    override var checkDuplicate: Iterable<E>.() -> Unit = {}
                    override var enableCheckExistNo = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface UpdateOptions<E> : SaveOptions<E> {

    var enableCheckExist: Boolean

    companion object {
        operator fun <E> invoke(another: ((UpdateOptions<E>) -> Unit)? = null) =
                object : UpdateOptions<E> {
                    override var checkDuplicate: E.() -> Unit = {}
                    override var enableCheckExist = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface UpdateAllOptions<E> : SaveAllOptions<E> {

    var enableCheckExist: Boolean

    companion object {
        operator fun <E> invoke(another: ((UpdateAllOptions<E>) -> Unit)? = null) =
                object : UpdateAllOptions<E> {
                    override var checkDuplicate: Iterable<E>.() -> Unit = {}
                    override var enableCheckExist = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}
