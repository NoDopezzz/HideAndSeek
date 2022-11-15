package nay.kirill.bluetooth.server.callback

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serverCallbackModule = module {
    singleOf(::ServerServiceCallback)
}