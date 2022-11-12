package nay.kirill.hideandseek.sessionsearch.impl.presentation

import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

internal data class SessionSearchState(
        val counter: Int
)

internal sealed interface SessionSearchUiState {

    object Loading : SessionSearchUiState

    data class Content(
            val sessions: List<SessionUiState>
    ) : SessionSearchUiState

    object Error : SessionSearchUiState

}
