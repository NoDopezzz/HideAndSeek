package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch.SessionSearchFragment

internal class WaitingNavigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openSessionSearching() = router.replaceScreen(FragmentScreen {
        SessionSearchFragment.newInstance()
    })

}