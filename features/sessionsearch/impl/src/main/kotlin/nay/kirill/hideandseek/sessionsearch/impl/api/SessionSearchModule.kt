package nay.kirill.hideandseek.sessionsearch.impl.api

import nay.kirill.hideandseek.sessionsearch.api.SessionSearchApi
import nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch.SessionSearchStateConverter
import nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch.SessionSearchViewModel
import nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch.SessionSearchNavigation
import nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting.WaitingStateConverter
import nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting.WaitingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val sessionSearchModule = module {
    factory<SessionSearchApi> { SessionSearchApiImpl() }
    factoryOf(::SessionSearchNavigation)
    viewModelOf(::SessionSearchViewModel)
    factoryOf(::SessionSearchStateConverter)

    factoryOf(::WaitingStateConverter)
    viewModel { prop ->
        WaitingViewModel(prop.get(), get())
    }
}