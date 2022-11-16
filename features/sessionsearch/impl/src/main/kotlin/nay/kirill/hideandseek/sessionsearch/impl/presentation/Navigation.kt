package nay.kirill.hideandseek.sessionsearch.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.sessionsearch.impl.presentation.timer.HideTimerFragment
import nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch.SessionSearchFragment
import nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting.WaitingArgs
import nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting.WaitingFragment

internal class Navigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openSessionSearching() = router.replaceScreen(FragmentScreen {
        SessionSearchFragment.newInstance()
    })

    fun openWaiting(args: WaitingArgs) = router.replaceScreen(FragmentScreen {
        WaitingFragment.newInstance(args)
    })

    fun openHideCounter() = router.replaceScreen(FragmentScreen {
        HideTimerFragment.newInstance()
    })

}