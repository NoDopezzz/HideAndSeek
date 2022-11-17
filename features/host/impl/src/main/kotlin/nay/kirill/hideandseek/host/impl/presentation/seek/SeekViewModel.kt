package nay.kirill.hideandseek.host.impl.presentation.seek

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.messages.Message
import nay.kirill.bluetooth.server.callback.event.ServerEvent
import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.hideandseek.host.impl.presentation.HostNavigation
import nay.kirill.hideandseek.host.impl.presentation.seek.models.Location

internal class SeekViewModel(
        converter: SeekStateConverter,
        serverEventCallback: ServerEventCallback,
        private val navigation: HostNavigation
): BaseEffectViewModel<SeekState, SeekUiState, SeekEffect>(
        converter = converter,
        initialState = SeekState.Content(locations = mapOf())
) {

    init {
        serverEventCallback.result
                .onEach(::handleEvent)
                .launchIn(viewModelScope)
    }

    fun back() = navigation.back()

    fun retry() = navigation.openHosting()

    private fun handleEvent(event: ServerEvent) {
        when {
            event is ServerEvent.OnFatalException -> {
                _effect.trySend(SeekEffect.StopService)
                state = SeekState.Error
            }
            event is ServerEvent.OnDeviceDisconnected && event.deviceCount == 0 -> {
                _effect.trySend(SeekEffect.StopService)
                state = SeekState.NoDevicesConnected
            }
            event is ServerEvent.OnMinorException -> {
                _effect.trySend(SeekEffect.ShowToast(message = "Произошла ошибка ${event.throwable.message}"))
            }
            event is ServerEvent.OnNewMessage && event.message is Message.Location && state is SeekState.Content -> {
                val updatedLocations = (state as SeekState.Content).locations.toMutableMap()
                updatedLocations[event.device.address] = Location(
                        latitude = (event.message as Message.Location).latitude,
                        longitude = (event.message as Message.Location).longitude
                )

                state = SeekState.Content(locations = updatedLocations)
            }
        }
    }
}
