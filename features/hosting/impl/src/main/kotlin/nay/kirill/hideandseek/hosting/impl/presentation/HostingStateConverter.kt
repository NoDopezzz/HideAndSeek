package nay.kirill.hideandseek.hosting.impl.presentation

import android.annotation.SuppressLint
import android.content.Context
import nay.kirill.core.arch.ContentEvent
import nay.kirill.core.utils.permissions.PermissionsUtils
import nay.kirill.hideandseek.hosting.impl.R
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

internal class HostingStateConverter(
        private val context: Context
) : (HostingState) -> HostingUiState {

    @SuppressLint("MissingPermission")
    override fun invoke(state: HostingState): HostingUiState = when {
        !PermissionsUtils.checkAdvertisingPermissions(context) -> HostingUiState.Error
        state.connectedDeviceEvent is ContentEvent.Error -> HostingUiState.Error
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
                subtitleId = R.string.hosting_subtitle
        )
    }
}