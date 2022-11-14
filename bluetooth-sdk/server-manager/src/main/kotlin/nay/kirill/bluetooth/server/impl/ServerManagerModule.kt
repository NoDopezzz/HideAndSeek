package nay.kirill.bluetooth.server.impl

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val serverManagerModule = module {
    factoryOf(::ServerManager)
}