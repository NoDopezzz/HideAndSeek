package nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting.WaitingArgs
import nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting.WaitingFragment

internal class SessionSearchNavigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openWaiting(args: WaitingArgs) = router.replaceScreen(FragmentScreen {
        WaitingFragment.newInstance(args)
    })

}
