package nay.kirill.hideandseek.mainmenu.impl.presentation

import com.github.terrakok.cicerone.Router
import nay.kirill.hideandseek.sessionsearch.api.SessionSearchApi

internal class MainMenuNavigation(
    private val router: Router,
    private val sessionSearchApi: SessionSearchApi
) {

    fun openCreateSession() {
        // TODO add navigation to creating session
    }

    fun openConnectToSession() {
        router.navigateTo(sessionSearchApi.getSessionSearchScreen())
    }

}
