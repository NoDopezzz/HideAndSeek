package nay.kirill.hideandseek.hosting.impl.api

import nay.kirill.hideandseek.hosting.api.HostingApi
import nay.kirill.hideandseek.hosting.impl.presentation.HostingStateConverter
import nay.kirill.hideandseek.hosting.impl.presentation.HostingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val hostingModule = module {
    factoryOf(::HostingApiImpl).withOptions { bind<HostingApi>() }
    factoryOf(::HostingStateConverter)
    viewModelOf(::HostingViewModel)
}