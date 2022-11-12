package nay.kirill.hideandseek.sessionsearch.impl.api

import nay.kirill.hideandseek.sessionsearch.api.SessionSearchApi
import nay.kirill.hideandseek.sessionsearch.impl.presentation.SessionSearchStateConverter
import nay.kirill.hideandseek.sessionsearch.impl.presentation.SessionSearchViewModel
import nay.kirill.hideandseek.sessionsearch.impl.presentation.SessionSearchNavigation
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val sessionSearchModule = module {
    factory<SessionSearchApi> { SessionSearchApiImpl() }
    factoryOf(::SessionSearchNavigation)
    viewModelOf(::SessionSearchViewModel)
    factoryOf(::SessionSearchStateConverter)
}