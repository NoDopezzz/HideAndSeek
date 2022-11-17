package nay.kirill.hideandseek

import android.app.Application
import com.google.android.gms.common.ConnectionResult as GConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.ConnectionResult as HConnectionResult
import com.huawei.hms.api.HuaweiApiAvailability
import nay.kirill.bluetooth.client.callback.clientCallbackModule
import nay.kirill.bluetooth.client.clientManagerModule
import nay.kirill.bluetooth.scanner.impl.bluetoothScannerModule
import nay.kirill.bluetooth.server.callback.serverCallbackModule
import nay.kirill.core.ui.res.resourceModule
import nay.kirill.hideandseek.di.mainModule
import nay.kirill.hideandseek.host.impl.api.hostingModule
import nay.kirill.hideandseek.mainmenu.impl.api.mainMenuModule
import nay.kirill.hideandseek.navigation.navigationModule
import nay.kirill.hideandseek.client.impl.api.sessionSearchModule
import nay.kirill.location.google.googleLocationModule
import nay.kirill.location.huawei.huaweiLocationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(appModules)
        }
    }

    private val appModules by lazy {
        listOfNotNull(
                navigationModule,
                mainMenuModule,
                mainModule,
                sessionSearchModule,
                bluetoothScannerModule,
                hostingModule,
                resourceModule,
                clientManagerModule,
                serverCallbackModule,
                clientCallbackModule,
                getLocationModule()
        )
    }

    private fun getLocationModule() = when {
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
                == GConnectionResult.SUCCESS -> googleLocationModule
        HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(this)
                == HConnectionResult.SUCCESS -> huaweiLocationModule
        else -> null
    }

}