package nay.kirill.hideandseek.client.impl.presentation.timer

import android.content.Context
import nay.kirill.core.ui.timer.TimerState
import nay.kirill.core.utils.permissions.PermissionsUtils

internal class HideTimerConverter(
        private val context: Context
): (HideCounterState) -> HideCounterUiState {

    override fun invoke(state: HideCounterState): HideCounterUiState = when {
        !PermissionsUtils.checkScanningPermissions(context) -> HideCounterUiState.Error
        state is HideCounterState.Content -> HideCounterUiState.Content(
                timerState = TimerState(
                        time = state.count.toString(),
                        progress = state.count / 60F
                )
        )
        else -> HideCounterUiState.Error
    }
}