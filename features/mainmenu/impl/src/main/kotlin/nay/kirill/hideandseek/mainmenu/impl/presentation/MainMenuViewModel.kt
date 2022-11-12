package nay.kirill.hideandseek.mainmenu.impl.presentation

import androidx.lifecycle.ViewModel

internal class MainMenuViewModel(
    private val navigation: MainMenuNavigation
) : ViewModel() {

    fun onCreateSession() {
        navigation.openCreateSession()
    }

    fun onConnectToSession() {
        navigation.openConnectToSession()
    }

}
