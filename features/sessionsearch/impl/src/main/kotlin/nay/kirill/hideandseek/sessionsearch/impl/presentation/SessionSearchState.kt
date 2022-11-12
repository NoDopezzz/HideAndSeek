package nay.kirill.hideandseek.sessionsearch.impl.presentation

internal data class SessionSearchState(
        val counter: Int
)

internal sealed interface SessionSearchUiState {

    object Loading : SessionSearchUiState

    data class Content(
            val sessions: List<String>
    ) : SessionSearchUiState

    object Error : SessionSearchUiState

}
