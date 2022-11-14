package nay.kirill.hideandseek.hosting.impl.presentation

import android.annotation.SuppressLint
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.R
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

internal class HostingStateConverter : (HostingState) -> HostingUiState {

    @SuppressLint("MissingPermission")
    override fun invoke(state: HostingState): HostingUiState = when (state.connectedDeviceEvent) {
        is ContentEvent.Error -> HostingUiState.Error(
                titleId = R.string.hosting_error_title,
                subtitleId = R.string.hosting_error_subtitle,
                primaryButtonAction = ButtonAction.Retry,
                secondaryButtonAction = ButtonAction.Back
        )
        else -> HostingUiState.Content(
                connectedDevices = state.connectedDeviceEvent
                        .data
                        ?.map { device ->
                            ConnectedDeviceUiState(
                                    deviceId = device.address,
                                    name = device.name ?: device.address
                            )
                        }
                        .orEmpty(),
                hostDeviceName = state.hostDeviceName,
                isPrimaryButtonVisible = !state.connectedDeviceEvent.data.isNullOrEmpty(),
                titleId = R.string.hosting_title,
                subtitleId = R.string.hosting_subtitle,
                primaryButtonAction = ButtonAction.Start,
                secondaryButtonAction = ButtonAction.Back
        )
    }
}