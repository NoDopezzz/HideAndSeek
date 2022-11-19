package nay.kirill.hideandseek.host.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.host.impl.presentation.foundinfo.FoundInfoArgs
import nay.kirill.hideandseek.host.impl.presentation.foundinfo.FoundInfoFragment
import nay.kirill.hideandseek.host.impl.presentation.hosting.HostingFragment
import nay.kirill.hideandseek.host.impl.presentation.seek.SeekArgs
import nay.kirill.hideandseek.host.impl.presentation.seek.SeekFragment
import nay.kirill.hideandseek.host.impl.presentation.timer.SeekTimerArgs
import nay.kirill.hideandseek.host.impl.presentation.timer.SeekTimerFragment

internal class HostNavigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openHosting() = router.replaceScreen(FragmentScreen {
        HostingFragment.newInstance()
    })

    fun openTimer(args: SeekTimerArgs) = router.replaceScreen(FragmentScreen {
        SeekTimerFragment.newInstance(args)
    })

    fun openSeek(args: SeekArgs) = router.replaceScreen(FragmentScreen {
        SeekFragment.newInstance(args)
    })

    fun navigateToFoundInfo(args: FoundInfoArgs) = router.navigateTo(FragmentScreen {
        FoundInfoFragment.newInstance(args)
    })

    fun replaceFoundInfo(args: FoundInfoArgs) = router.replaceScreen(FragmentScreen {
        FoundInfoFragment.newInstance(args)
    })

}
