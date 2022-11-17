package nay.kirill.hideandseek.client.impl.api

import nay.kirill.hideandseek.client.api.SessionSearchApi
import nay.kirill.hideandseek.client.impl.presentation.ClientNavigation
import nay.kirill.hideandseek.client.impl.presentation.hide.HideStateConverter
import nay.kirill.hideandseek.client.impl.presentation.hide.HideViewModel
import nay.kirill.hideandseek.client.impl.presentation.timer.HideTimerConverter
import nay.kirill.hideandseek.client.impl.presentation.timer.HideTimerViewModel
import nay.kirill.hideandseek.client.impl.presentation.sessionSearch.SessionSearchStateConverter
import nay.kirill.hideandseek.client.impl.presentation.sessionSearch.SessionSearchViewModel
import nay.kirill.hideandseek.client.impl.presentation.waiting.WaitingStateConverter
import nay.kirill.hideandseek.client.impl.presentation.waiting.WaitingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val sessionSearchModule = module {
    factory<SessionSearchApi> { SessionSearchApiImpl() }
    factoryOf(::ClientNavigation)
    viewModelOf(::SessionSearchViewModel)
    factoryOf(::SessionSearchStateConverter)

    factoryOf(::WaitingStateConverter)
    viewModel { prop ->
        WaitingViewModel(prop.get(), get(), get(), get())
    }

    factoryOf(::HideTimerConverter)
    viewModelOf(::HideTimerViewModel)

    factoryOf(::HideStateConverter)
    viewModelOf(::HideViewModel)
}