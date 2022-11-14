package nay.kirill.hideandseek.hosting.impl.presentation

import nay.kirill.core.arch.BaseViewModel

internal class HostingViewModel(
        private val converter: HostingStateConverter
) : BaseViewModel<HostingState, HostingUiState>(
        converter = converter,
        initialState = HostingState()
) {
}
