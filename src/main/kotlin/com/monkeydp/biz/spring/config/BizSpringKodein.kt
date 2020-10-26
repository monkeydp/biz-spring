package com.monkeydp.biz.spring.config

import com.monkeydp.biz.spring.config.BizSpringKMContainer.reverseModuleArray
import com.monkeydp.tools.config.toolsKodeinModule
import com.monkeydp.tools.ext.kodein.AbstractKodeinModuleContainer
import org.kodein.di.Kodein

/**
 * @author iPotato-Work
 * @date 2020/5/14
 */
val bizSpringKodeinModule = Kodein.Module("bizSpringKodeinModule") {
    BizSpringKMContainer.addModule(toolsKodeinModule)
    importAll(*reverseModuleArray, allowOverride = true)
}

internal val kodein = Kodein.lazy {
    import(bizSpringKodeinModule, allowOverride = true)
    BizSpringKMContainer.logRegistered(*reverseModuleArray)
}

object BizSpringKMContainer : AbstractKodeinModuleContainer("BizSpringKodein")
