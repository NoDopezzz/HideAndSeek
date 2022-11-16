package nay.kirill.hideandseek.sessionsearch.impl.presentation.counter

import nay.kirill.core.arch.BaseViewModel
import nay.kirill.hideandseek.sessionsearch.impl.presentation.Navigation

internal class HideCounterViewModel(
        converter: HideCounterConverter,
        private val navigation: Navigation
) : BaseViewModel<HideCounterState, HideCounterUiState>(
        converter = converter,
        initialState = HideCounterState.Content(count = 60)
) {

    fun back() = navigation.back()

    fun retry() = navigation.openSessionSearching()

}
