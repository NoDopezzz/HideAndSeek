package nay.kirill.hideandseek.mainmenu.impl.api

import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.hideandseek.mainmenu.api.MainMenuApi
import nay.kirill.hideandseek.mainmenu.impl.presentation.MainMenuFragment

internal class MainMenuApiImpl : MainMenuApi {

    override fun getMainMenuScreen(): FragmentScreen = FragmentScreen {
        MainMenuFragment.newInstance()
    }

}
