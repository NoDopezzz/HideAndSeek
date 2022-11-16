package nay.kirill.hideandseek.hosting.impl.presentation

import android.bluetooth.BluetoothDevice
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

internal data class HostingState(
        val hostDeviceName: String,
        val connectedDeviceEvent: ContentEvent<List<BluetoothDevice>>
)

internal sealed interface HostingUiState {

    data class Content(
            val connectedDevices: List<ConnectedDeviceUiState>,
            val hostDeviceName: String,
            val titleId: Int,
            val subtitleId: Int,
            val isPrimaryButtonVisible: Boolean
    ) : HostingUiState

    object Error : HostingUiState

}