package nay.kirill.hideandseek.mainmenu.impl.presentation

import com.github.terrakok.cicerone.Router
import nay.kirill.hideandseek.host.api.HostingApi
import nay.kirill.hideandseek.client.api.SessionSearchApi

internal class MainMenuNavigation(
        private val router: Router,
        private val sessionSearchApi: SessionSearchApi,
        private val hostingApi: HostingApi
) {

    fun openCreateSession() {
        router.navigateTo(hostingApi.getHostingScreen())
    }

    fun openConnectToSession() {
        router.navigateTo(sessionSearchApi.getSessionSearchScreen())
    }

}
