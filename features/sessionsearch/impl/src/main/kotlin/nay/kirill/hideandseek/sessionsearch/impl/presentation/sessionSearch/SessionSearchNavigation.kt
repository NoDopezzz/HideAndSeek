package nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch

import com.github.terrakok.cicerone.Router

internal class SessionSearchNavigation(
        private val router: Router
) {

    fun back() = router.exit()

}
