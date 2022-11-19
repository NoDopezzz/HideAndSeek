package nay.kirill.hideandseek.host.impl.presentation.timer

import androidx.annotation.StringRes
import nay.kirill.core.ui.timer.TimerState

sealed interface SeekTimerState {

    data class Content(val count: Int) : SeekTimerState

    object Error : SeekTimerState

    object NoDevicesConnected : SeekTimerState

}

sealed interface SeekTimerUiState {

    data class Content(
            val timerState: TimerState
    ) : SeekTimerUiState

    data class Error(
            @StringRes val descriptionId: Int
    ) : SeekTimerUiState

}