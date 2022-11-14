package nay.kirill.hideandseek.hosting.impl.presentation

import nay.kirill.bluetooth.scanner.api.ScannedDevice
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction

internal class HostingViewModel(
        converter: HostingStateConverter
) : BaseViewModel<HostingState, HostingUiState>(
        converter = converter,
        initialState = HostingState(
                connectedDeviceEvent = ContentEvent.Loading(),
                hostDevice = ScannedDevice("", "")
        )
) {

    fun onButtonClicked(buttonAction: ButtonAction) {
        // TODO
    }

}
