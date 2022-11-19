package nay.kirill.hideandseek.client.impl.presentation.views

internal data class SessionUiState(
        val deviceAddress: String,
        val name: String,
        val isLoading: Boolean = false
)
