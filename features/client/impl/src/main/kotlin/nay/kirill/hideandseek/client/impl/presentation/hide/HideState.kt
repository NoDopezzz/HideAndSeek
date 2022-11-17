package nay.kirill.hideandseek.client.impl.presentation.hide

sealed interface HideState {

    data class Content(
            val deviceAddress: String?
    ) : HideState

    object Error : HideState

}

sealed interface HideUiState {

    data class Content(
            val deviceAddress: String?
    ) : HideUiState

    object Error : HideUiState

}