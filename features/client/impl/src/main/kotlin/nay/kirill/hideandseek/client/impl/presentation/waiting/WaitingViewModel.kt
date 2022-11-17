package nay.kirill.hideandseek.client.impl.presentation.waiting

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.bluetooth.messages.MessageConstants
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.hideandseek.client.impl.presentation.ClientNavigation

internal class WaitingViewModel(
        args: WaitingArgs,
        converter: WaitingStateConverter,
        private val navigation: ClientNavigation,
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
        when {
            event is ClientEvent.OnFailure -> {
                state = WaitingState.Error
            }
            event is ClientEvent.OnNewMessage && event.message == MessageConstants.START -> {
                navigation.openHideCounter()
            }
            else -> Unit
        }
    }

    fun back() = navigation.back()

    fun openSessionSearching() = navigation.openSessionSearching()

}
