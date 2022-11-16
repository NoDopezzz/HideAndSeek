package nay.kirill.hideandseek.client.impl.api

import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.client.api.SessionSearchApi
import nay.kirill.hideandseek.client.impl.presentation.sessionSearch.SessionSearchFragment

internal class SessionSearchApiImpl : SessionSearchApi {

    override fun getSessionSearchScreen(): FragmentScreen = FragmentScreen {
        SessionSearchFragment.newInstance()
    }
}