package nay.kirill.hideandseek.hosting.impl.presentation.hosting

import android.bluetooth.BluetoothDevice
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.hosting.models.ConnectedDeviceUiState

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
            val isPrimaryButtonVisible: Boolean,
            val isLoadingVisible: Boolean
    ) : HostingUiState

    object Error : HostingUiState

}