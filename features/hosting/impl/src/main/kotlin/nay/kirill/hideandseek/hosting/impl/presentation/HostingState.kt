package nay.kirill.hideandseek.hosting.impl.presentation

import nay.kirill.bluetooth.scanner.api.ScannedDevice
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

internal data class HostingState(
        val hostDeviceName: String,
        val connectedDeviceEvent: ContentEvent<List<ScannedDevice>>
)

internal sealed interface HostingUiState {

    val titleId: Int

    val subtitleId: Int

    val primaryButtonAction: ButtonAction

    val secondaryButtonAction: ButtonAction

    val isPrimaryButtonVisible: Boolean

    data class Content(
            val connectedDevices: List<ConnectedDeviceUiState>,
            val hostDeviceName: String,
            override val titleId: Int,
            override val subtitleId: Int,
            override val primaryButtonAction: ButtonAction,
            override val secondaryButtonAction: ButtonAction,
            override val isPrimaryButtonVisible: Boolean
    ) : HostingUiState

    data class Error(
            override val titleId: Int,
            override val subtitleId: Int,
            override val primaryButtonAction: ButtonAction,
            override val secondaryButtonAction: ButtonAction
    ) : HostingUiState {

        override val isPrimaryButtonVisible: Boolean = true

    }

}