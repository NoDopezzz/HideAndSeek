package nay.kirill.hideandseek.navigation

import com.github.terrakok.cicerone.Router
import nay.kirill.hideandseek.mainmenu.api.MainMenuApi

internal class Navigation(
    private val router: Router,
    private val mainApi: MainMenuApi
) {

    fun openMainMenu() = router.newRootScreen(mainApi.getMainMenuScreen())

}
