package nay.kirill.hideandseek.hosting.impl.api

import nay.kirill.hideandseek.hosting.api.HostingApi
import nay.kirill.hideandseek.hosting.impl.presentation.HostingNavigation
import nay.kirill.hideandseek.hosting.impl.presentation.hosting.HostingStateConverter
import nay.kirill.hideandseek.hosting.impl.presentation.hosting.HostingViewModel
import nay.kirill.hideandseek.hosting.impl.presentation.timer.SeekTimerConverter
import nay.kirill.hideandseek.hosting.impl.presentation.timer.SeekTimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val hostingModule = module {
    factoryOf(::HostingApiImpl).withOptions { bind<HostingApi>() }
    factoryOf(::HostingStateConverter)
    factoryOf(::HostingNavigation)
    viewModelOf(::HostingViewModel)

    viewModelOf(::SeekTimerViewModel)
    factoryOf(::SeekTimerConverter)
}