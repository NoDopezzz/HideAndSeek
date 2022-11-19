package nay.kirill.hideandseek

import androidx.lifecycle.ViewModel
import nay.kirill.hideandseek.navigation.Navigation

internal class MainViewModel(
        private val navigation: Navigation
) : ViewModel() {

    fun openMain() = navigation.openMainMenu()

}
