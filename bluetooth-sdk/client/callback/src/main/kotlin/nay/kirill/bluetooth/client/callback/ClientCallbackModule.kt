package nay.kirill.bluetooth.client.callback

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val clientCallbackModule = module {
    singleOf(::ClientServiceCallback)
}