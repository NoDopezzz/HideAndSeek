package nay.kirill.hideandseek.sessionsearch.impl.presentation.counter

internal sealed interface HideCounterState {

    data class Content(val count: Int) : HideCounterState

    object Error : HideCounterState

}

internal sealed interface HideCounterUiState {

    data class Content(
            val seconds: String,
            val progress: Float
    ) : HideCounterUiState

    object Error : HideCounterUiState

}
