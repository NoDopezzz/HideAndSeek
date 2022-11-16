package nay.kirill.hideandseek.host.impl.presentation.timer

import android.content.Context
import nay.kirill.core.ui.timer.TimerState
import nay.kirill.core.utils.permissions.PermissionsUtils
import nay.kirill.hideandseek.host.impl.R

internal class SeekTimerConverter(
        private val context: Context
) : (SeekTimerState) -> SeekTimerUiState {

    override fun invoke(state: SeekTimerState): SeekTimerUiState = when {
        state is SeekTimerState.Content -> SeekTimerUiState.Content(
                timerState = TimerState(
                        time = state.count.toString(),
                        progress = state.count / 60F
                )
        )
        !PermissionsUtils.checkScanningPermissions(context) -> SeekTimerUiState.Error(R.string.no_permissions_error_description)
        state is SeekTimerState.NoDevicesConnected -> SeekTimerUiState.Error(R.string.no_connections_error_description)
        else -> SeekTimerUiState.Error(R.string.common_error_description)
    }
}