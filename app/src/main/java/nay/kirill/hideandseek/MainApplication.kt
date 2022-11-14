package nay.kirill.hideandseek

import android.app.Application
import nay.kirill.bluetooth.scanner.impl.bluetoothScannerModule
import nay.kirill.bluetooth.server.impl.serverManagerModule
import nay.kirill.core.ui.res.resourceModule
import nay.kirill.hideandseek.di.mainModule
import nay.kirill.hideandseek.hosting.impl.api.hostingModule
import nay.kirill.hideandseek.mainmenu.impl.api.mainMenuModule
import nay.kirill.hideandseek.navigation.navigationModule
import nay.kirill.hideandseek.sessionsearch.impl.api.sessionSearchModule
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

    private val appModules = listOf(
            navigationModule,
            mainMenuModule,
            mainModule,
            sessionSearchModule,
            bluetoothScannerModule,
            hostingModule,
            resourceModule,
            serverManagerModule
    )

}