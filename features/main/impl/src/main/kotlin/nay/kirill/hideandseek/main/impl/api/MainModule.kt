package nay.kirill.hideandseek.main.impl.api

import nay.kirill.hideandseek.main.api.MainApi
import org.koin.dsl.module

val mainModule = module {
    factory<MainApi> { MainApiImpl() }
}