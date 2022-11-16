package nay.kirill.hideandseek.sessionsearch.impl.presentation.timer

import nay.kirill.core.ui.timer.TimerState

internal sealed interface HideCounterState {

    data class Content(val count: Int) : HideCounterState

    object Error : HideCounterState

}

internal sealed interface HideCounterUiState {

    data class Content(
            val timerState: TimerState
    ) : HideCounterUiState

    object Error : HideCounterUiState

}
