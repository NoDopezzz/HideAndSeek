package nay.kirill.hideandseek.main.impl.api

import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.main.api.MainApi
import nay.kirill.hideandseek.main.impl.presentation.MainFragment

internal class MainApiImpl : MainApi {

    override fun getMainScreen(): FragmentScreen = FragmentScreen {
        MainFragment.newInstance()
    }

}
