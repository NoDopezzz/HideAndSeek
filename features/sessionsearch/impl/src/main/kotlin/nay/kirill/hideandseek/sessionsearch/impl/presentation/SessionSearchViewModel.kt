package nay.kirill.hideandseek.sessionsearch.impl.presentation

import nay.kirill.core.arch.BaseViewModel

internal class SessionSearchViewModel(
        converter: SessionSearchStateConverter,
        private val navigation: SessionSearchNavigation
) : BaseViewModel<SessionSearchState, SessionSearchUiState>(
        converter = converter,
        initialState = SessionSearchState(0)
) {

    fun back() = navigation.back()

}
