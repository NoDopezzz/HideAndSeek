package nay.kirill.hideandseek.hosting.impl.presentation

import nay.kirill.core.arch.ContentEvent
import nay.kirill.core.ui.res.ResourceProvider
import nay.kirill.hideandseek.hosting.impl.R
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

internal class HostingStateConverter(
        private val resources: ResourceProvider
) : (HostingState) -> HostingUiState {

    override fun invoke(state: HostingState): HostingUiState = when (state.connectedDeviceEvent) {
        is ContentEvent.Error -> HostingUiState.Error(
                titleId = R.string.hosting_error_title,
                subtitle = resources.getString(R.string.hosting_error_subtitle),
                primaryButtonAction = ButtonAction.Retry,
                secondaryButtonAction = ButtonAction.Back
        )
        else -> HostingUiState.Content(
                connectedDevices = state.connectedDeviceEvent
                        .data
                        ?.map { device ->
                            ConnectedDeviceUiState(
                                    deviceId = device.address,
                                    name = device.name
                            )
                        }
                        .orEmpty(),
                titleId = R.string.hosting_title,
                subtitle = resources.getString(R.string.hosting_subtitle, state.hostDevice.name),
                primaryButtonAction = ButtonAction.Start,
                secondaryButtonAction = ButtonAction.Back
        )
    }
}