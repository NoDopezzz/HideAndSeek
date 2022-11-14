package nay.kirill.hideandseek.hosting.impl.presentation

import com.github.terrakok.cicerone.Router

internal class HostingNavigation(
        private val router: Router
) {

    fun back() = router.exit()

}
