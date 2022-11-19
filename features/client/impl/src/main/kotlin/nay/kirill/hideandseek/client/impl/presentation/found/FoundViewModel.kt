package nay.kirill.hideandseek.client.impl.presentation.found

import androidx.lifecycle.ViewModel
import nay.kirill.hideandseek.client.impl.presentation.ClientNavigation

internal class FoundViewModel(
        private val navigation: ClientNavigation
) : ViewModel() {

    fun back() = navigation.back()

}