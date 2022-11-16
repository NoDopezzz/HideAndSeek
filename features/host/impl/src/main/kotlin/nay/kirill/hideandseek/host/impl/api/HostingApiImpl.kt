package nay.kirill.hideandseek.host.impl.api

import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.host.impl.presentation.hosting.HostingFragment
import nay.kirill.hideandseek.host.api.HostingApi

internal class HostingApiImpl : HostingApi {

    override fun getHostingScreen(): FragmentScreen = FragmentScreen {
        HostingFragment.newInstance()
    }

}
