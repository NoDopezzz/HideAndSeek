package nay.kirill.hideandseek.hosting.impl.presentation

import nay.kirill.bluetooth.scanner.api.ScannedDevice
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

internal data class HostingState(
        val hostDevice: ScannedDevice,
        val connectedDeviceEvent: ContentEvent<List<ScannedDevice>>
)

internal sealed interface HostingUiState {

    val titleId: Int

    val subtitle: String

    val primaryButtonAction: ButtonAction

    val secondaryButtonAction: ButtonAction

    data class Content(
            val connectedDevices: List<ConnectedDeviceUiState>,
            override val titleId: Int,
            override val subtitle: String,
            override val primaryButtonAction: ButtonAction,
            override val secondaryButtonAction: ButtonAction
    ) : HostingUiState

    data class Error(
            override val titleId: Int,
            override val subtitle: String,
            override val primaryButtonAction: ButtonAction,
            override val secondaryButtonAction: ButtonAction
    ) : HostingUiState

}