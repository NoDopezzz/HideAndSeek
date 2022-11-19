package nay.kirill.location.google

import nay.kirill.location.api.LocationManager
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val googleLocationModule = module {
    factoryOf(::LocationManagerGoogleImpl).bind<LocationManager>()
}