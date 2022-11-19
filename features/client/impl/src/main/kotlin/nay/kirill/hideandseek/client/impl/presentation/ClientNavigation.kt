package nay.kirill.hideandseek.client.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.client.impl.presentation.found.FoundFragment
import nay.kirill.hideandseek.client.impl.presentation.hide.HideFragment
import nay.kirill.hideandseek.client.impl.presentation.timer.HideTimerFragment
import nay.kirill.hideandseek.client.impl.presentation.sessionSearch.SessionSearchFragment
import nay.kirill.hideandseek.client.impl.presentation.waiting.WaitingArgs
import nay.kirill.hideandseek.client.impl.presentation.waiting.WaitingFragment

internal class ClientNavigation(
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

    fun openHide() = router.replaceScreen(FragmentScreen {
        HideFragment.newInstance()
    })

    fun openFound() = router.replaceScreen(FragmentScreen {
        FoundFragment.newInstance()
    })

}