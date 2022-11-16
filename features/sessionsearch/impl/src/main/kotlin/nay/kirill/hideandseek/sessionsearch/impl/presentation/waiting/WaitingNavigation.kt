package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import com.github.terrakok.cicerone.Router

internal class WaitingNavigation(
        private val router: Router
) {

    fun back() = router.exit()

}