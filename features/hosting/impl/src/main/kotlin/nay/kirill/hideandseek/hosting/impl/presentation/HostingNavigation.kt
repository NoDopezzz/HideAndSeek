package nay.kirill.hideandseek.hosting.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.hosting.impl.presentation.hosting.HostingFragment
import nay.kirill.hideandseek.hosting.impl.presentation.timer.SeekTimerFragment

internal class HostingNavigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openHosting() = router.replaceScreen(FragmentScreen {
        HostingFragment.newInstance()
    })

    fun openTimer() = router.replaceScreen(FragmentScreen {
        SeekTimerFragment.newInstance()
    })

}
