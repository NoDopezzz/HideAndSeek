package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import nay.kirill.core.arch.BaseViewModel

internal class WaitingViewModel(
        args: WaitingArgs,
        converter: WaitingStateConverter,
        private val navigation: WaitingNavigation
) : BaseViewModel<WaitingState, WaitingUiState>(
        converter = converter,
        initialState = WaitingState.Content(serverDevice = args.bluetoothDevice)
) {

    fun back() = navigation.back()

}
