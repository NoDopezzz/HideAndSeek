package nay.kirill.hideandseek

import android.app.Application
import nay.kirill.hideandseek.di.baseModule
import nay.kirill.hideandseek.main.impl.api.mainModule
import nay.kirill.hideandseek.navigation.navigationModule
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
        mainModule,
        baseModule
    )

}