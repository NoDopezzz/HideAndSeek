package nay.kirill.hideandseek.hosting.impl.presentation

import nay.kirill.bluetooth.scanner.api.ScannedDevice
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction

internal class HostingViewModel(
        converter: HostingStateConverter,
        private val navigation: HostingNavigation
) : BaseViewModel<HostingState, HostingUiState>(
        converter = converter,
        initialState = HostingState(
                connectedDeviceEvent = ContentEvent.Loading(),
                hostDevice = ScannedDevice("", "")
        )
) {

    fun onButtonClicked(buttonAction: ButtonAction) {
        when (buttonAction) {
            is ButtonAction.Start -> TODO()
            is ButtonAction.Retry -> TODO()
            is ButtonAction.Back -> navigation.back()
        }
    }

}
