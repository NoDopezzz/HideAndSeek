package nay.kirill.hideandseek.client.impl.presentation.hide

import android.content.Context
import nay.kirill.core.utils.permissions.PermissionsUtils

internal class HideStateConverter(
        private val context: Context
) : (HideState) -> HideUiState {

    override fun invoke(state: HideState): HideUiState = when {
        !PermissionsUtils.checkBluetoothConnectPermission(context) -> HideUiState.Error
        state is HideState.Content -> HideUiState.Content(deviceAddress = state.deviceAddress)
        else -> HideUiState.Error

    }
}