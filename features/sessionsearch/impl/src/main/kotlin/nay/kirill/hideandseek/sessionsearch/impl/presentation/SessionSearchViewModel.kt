package nay.kirill.hideandseek.sessionsearch.impl.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nay.kirill.core.arch.BaseViewModel

internal class SessionSearchViewModel(
    converter: SessionSearchStateConverter
) : BaseViewModel<SessionSearchState, SessionSearchUiState>(
    converter = converter,
    initialState = SessionSearchState(0)
) {

    init {
        viewModelScope.launch {
            for (i in 1..10) {
                delay(1000)
                state = state.copy(counter = i)
            }
        }
    }

}
