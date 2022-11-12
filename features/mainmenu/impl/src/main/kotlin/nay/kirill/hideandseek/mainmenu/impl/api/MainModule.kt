package nay.kirill.hideandseek.mainmenu.impl.api

import nay.kirill.hideandseek.mainmenu.api.MainMenuApi
import org.koin.dsl.module

val mainMenuModule = module {
    factory<MainMenuApi> { MainMenuApiImpl() }
}