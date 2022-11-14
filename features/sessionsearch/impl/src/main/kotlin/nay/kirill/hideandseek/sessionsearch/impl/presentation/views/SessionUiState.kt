package nay.kirill.hideandseek.sessionsearch.impl.presentation.views

internal data class SessionUiState(
        val deviceAddress: String,
        val name: String,
        val isLoading: Boolean = false
)
