package nay.kirill.hideandseek.client.impl.presentation.hide

import android.Manifest
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.client.ClientConfig
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.bluetooth.client.callback.message.ClientMessage
import nay.kirill.bluetooth.client.callback.message.ClientMessageCallback
import nay.kirill.bluetooth.messages.Message
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.hideandseek.client.impl.presentation.ClientNavigation
import nay.kirill.location.api.LocationManager

internal class HideViewModel(
        converter: HideStateConverter,
        clientEventCallback: ClientEventCallback,
        private val navigation: ClientNavigation,
        private val locationManager: LocationManager,
        private val clientMessageCallback: ClientMessageCallback
): BaseEffectViewModel<HideState, HideUiState, HideEffect>(
        converter = converter,
        initialState = HideState.Content(deviceAddress = ClientConfig.deviceAddress)
) {

    init {
        clientEventCallback.result
                .onEach(::handleEvent)
                .launchIn(viewModelScope)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun startLocationUpdating() {
        locationManager.getLocationFlow(interval = 5000)
                .onEach { location ->
                    clientMessageCallback.setResult(
                            value = ClientMessage.SendMessage(message = Message.Location(
                                    latitude = location.latitude,
                                    longitude = location.longitude
                            ))
                    )
                }
                .launchIn(viewModelScope)
    }

    fun back() {
        navigation.back()
    }

    fun retry() {
        navigation.openSessionSearching()
    }

    private fun handleEvent(event: ClientEvent) {
        Log.i("HideViewModel", "new event: $event")
        when {
            event is ClientEvent.OnFailure -> {
                _effect.trySend(HideEffect.StopService)
                state = HideState.Error
            }
            event is ClientEvent.OnNewMessage && event.message is Message.IsNear -> {

            }
            event is ClientEvent.OnNewMessage && event.message is Message.Found -> {
                _effect.trySend(HideEffect.StopService)
                navigation.openFound()
            }
            else -> Unit
        }
    }

}
