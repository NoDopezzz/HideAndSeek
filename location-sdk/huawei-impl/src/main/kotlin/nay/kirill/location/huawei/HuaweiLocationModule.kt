package nay.kirill.location.huawei

import nay.kirill.location.api.LocationManager
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val huaweiLocationModule = module {
    factoryOf(::LocationManagerHuaweiImpl).bind<LocationManager>()
}