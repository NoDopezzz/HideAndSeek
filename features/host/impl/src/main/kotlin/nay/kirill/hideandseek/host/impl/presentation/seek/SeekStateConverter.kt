package nay.kirill.hideandseek.host.impl.presentation.seek

import android.content.Context
import nay.kirill.core.utils.permissions.PermissionsUtils
import nay.kirill.hideandseek.host.impl.R

internal class SeekStateConverter(
        private val context: Context
) : (SeekState) -> SeekUiState {

    override fun invoke(state: SeekState): SeekUiState = when {
        !PermissionsUtils.checkBluetoothConnectPermission(context) -> SeekUiState.Error(
                descriptionId = R.string.no_permissions_error_description
        )
        state is SeekState.Content -> SeekUiState.Content(
                devicesLeft = state.leftDevicesCount,
                locations = state.locations.values.toList(),
                currentLocation = state.currentLocation,
                showButton = state.isScanning || state.locations.any { it.value.isNear },
                isScanningView = state.isScanning
        )
        state is SeekState.NoDevicesConnected -> SeekUiState.Error(R.string.no_connections_error_description)
        else -> SeekUiState.Error(descriptionId = R.string.common_error_description)
    }

}
