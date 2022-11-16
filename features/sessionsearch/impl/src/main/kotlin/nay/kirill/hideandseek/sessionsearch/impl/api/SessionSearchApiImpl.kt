package nay.kirill.hideandseek.sessionsearch.impl.api

import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.sessionsearch.api.SessionSearchApi
import nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch.SessionSearchFragment

internal class SessionSearchApiImpl : SessionSearchApi {

    override fun getSessionSearchScreen(): FragmentScreen = FragmentScreen {
        SessionSearchFragment.newInstance()
    }
}