package com.monkeydp.biz.spring.crud

/**
 * @author iPotato-Work
 * @date 2021/1/20
 */
interface SaveConfig<E> {
    var checkDuplicate: E.() -> Unit

    companion object {
        operator fun <E> invoke(another: ((SaveConfig<E>) -> Unit)? = null) =
                object : SaveConfig<E> {
                    override var checkDuplicate: E.() -> Unit = {}

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface SaveAllConfig<E> {
    var checkDuplicate: Iterable<E>.() -> Unit

    companion object {
        operator fun <E> invoke(another: ((SaveAllConfig<E>) -> Unit)? = null) =
                object : SaveAllConfig<E> {
                    override var checkDuplicate: Iterable<E>.() -> Unit = {}

                    init {
                        another?.invoke(this)
                    }
                }
    }
}


interface CreateConfig<E> : SaveConfig<E> {

    var enableCheckExistNo: Boolean

    companion object {
        operator fun <E> invoke(another: ((CreateConfig<E>) -> Unit)? = null) =
                object : CreateConfig<E> {
                    override var checkDuplicate: E.() -> Unit = {}
                    override var enableCheckExistNo = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface CreateAllConfig<E> : SaveAllConfig<E> {

    var enableCheckExistNo: Boolean

    companion object {
        operator fun <E> invoke(another: ((CreateAllConfig<E>) -> Unit)? = null) =
                object : CreateAllConfig<E> {
                    override var checkDuplicate: Iterable<E>.() -> Unit = {}
                    override var enableCheckExistNo = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface UpdateConfig<E> : SaveConfig<E> {

    var enableCheckExist: Boolean

    companion object {
        operator fun <E> invoke(another: ((UpdateConfig<E>) -> Unit)? = null) =
                object : UpdateConfig<E> {
                    override var checkDuplicate: E.() -> Unit = {}
                    override var enableCheckExist = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}

interface UpdateAllConfig<E> : SaveAllConfig<E> {

    var enableCheckExist: Boolean

    companion object {
        operator fun <E> invoke(another: ((UpdateAllConfig<E>) -> Unit)? = null) =
                object : UpdateAllConfig<E> {
                    override var checkDuplicate: Iterable<E>.() -> Unit = {}
                    override var enableCheckExist = false

                    init {
                        another?.invoke(this)
                    }
                }
    }
}
