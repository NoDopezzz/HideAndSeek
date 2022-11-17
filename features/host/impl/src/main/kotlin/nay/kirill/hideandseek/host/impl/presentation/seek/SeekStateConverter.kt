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
                devicesLeft = state.locations.size,
                locations = state.locations.values.map { "${it.latitude};${it.longitude}" }.toList(),
        )
        state is SeekState.NoDevicesConnected -> SeekUiState.Error(R.string.no_connections_error_description)
        else -> SeekUiState.Error(descriptionId = R.string.common_error_description)
    }

}
