package nay.kirill.hideandseek.hosting.impl.api

import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.hosting.api.HostingApi
import nay.kirill.hideandseek.hosting.impl.presentation.hosting.HostingFragment

internal class HostingApiImpl : HostingApi {

    override fun getHostingScreen(): FragmentScreen = FragmentScreen {
        HostingFragment.newInstance()
    }

}
