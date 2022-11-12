package nay.kirill.hideandseek.sessionsearch.impl.presentation

internal class SessionSearchStateConverter : (SessionSearchState) -> SessionSearchUiState {

    override fun invoke(state: SessionSearchState): SessionSearchUiState = SessionSearchUiState(
        counter = state.counter.toString()
    )
}