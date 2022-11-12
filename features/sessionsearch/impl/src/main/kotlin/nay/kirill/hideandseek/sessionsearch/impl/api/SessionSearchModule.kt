package nay.kirill.hideandseek.sessionsearch.impl.api

import nay.kirill.hideandseek.sessionsearch.api.SessionSearchApi
import nay.kirill.hideandseek.sessionsearch.impl.presentation.SessionSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val sessionSearchModule = module {
    factory<SessionSearchApi> { SessionSearchApiImpl() }
    viewModelOf(::SessionSearchViewModel)
}