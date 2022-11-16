package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.hideandseek.sessionsearch.impl.presentation.Navigation

internal class WaitingViewModel(
        args: WaitingArgs,
        converter: WaitingStateConverter,
        private val navigation: Navigation,
        private val clientEventCallback: ClientEventCallback
) : BaseViewModel<WaitingState, WaitingUiState>(
        converter = converter,
        initialState = WaitingState.Content(serverDevice = args.bluetoothDevice)
) {

    init {
        clientEventCallback.result
                .onEach(::handleClientEvent)
                .launchIn(viewModelScope)
    }

    private fun handleClientEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.OnFailure -> {
                state = WaitingState.Error
            }
            else -> Unit
        }
    }

    fun back() = navigation.back()

    fun openSessionSearching() = navigation.openSessionSearching()

}
