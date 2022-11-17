package nay.kirill.hideandseek.host.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.host.impl.presentation.hosting.HostingFragment
import nay.kirill.hideandseek.host.impl.presentation.seek.SeekFragment
import nay.kirill.hideandseek.host.impl.presentation.timer.SeekTimerFragment

internal class HostNavigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openHosting() = router.replaceScreen(FragmentScreen {
        HostingFragment.newInstance()
    })

    fun openTimer() = router.replaceScreen(FragmentScreen {
        SeekTimerFragment.newInstance()
    })

    fun openSeek() = router.replaceScreen(FragmentScreen {
        SeekFragment.newInstance()
    })

}
