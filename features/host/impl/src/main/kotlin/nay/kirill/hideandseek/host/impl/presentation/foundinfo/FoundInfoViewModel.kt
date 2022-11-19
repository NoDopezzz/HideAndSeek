package nay.kirill.hideandseek.host.impl.presentation.foundinfo

import androidx.lifecycle.ViewModel
import nay.kirill.hideandseek.host.impl.presentation.HostNavigation

internal class FoundInfoViewModel(
        private val navigation: HostNavigation
) : ViewModel() {

    fun onButtonClick() = navigation.back()

}
