package nay.kirill.hideandseek.navigation

import com.github.terrakok.cicerone.Router
import nay.kirill.hideandseek.main.api.MainApi

internal class Navigation(
    private val router: Router,
    private val mainApi: MainApi
) {

    fun openMainScreen() = router.newRootScreen(mainApi.getMainScreen())

}
