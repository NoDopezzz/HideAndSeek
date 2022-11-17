package nay.kirill.hideandseek.host.impl.presentation.seek

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.messages.Message
import nay.kirill.bluetooth.server.callback.event.ServerEvent
import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.core.ui.radar.RadarLocation
import nay.kirill.hideandseek.host.impl.presentation.HostNavigation
import nay.kirill.location.api.LocationManager

internal class SeekViewModel(
        converter: SeekStateConverter,
        serverEventCallback: ServerEventCallback,
        private val navigation: HostNavigation,
        private val locationManager: LocationManager
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

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun fetchCurrentLocation() {
        viewModelScope.launch {
            locationManager.getLocationFlow()
                    .onEach {
                        if (state is SeekState.Content) {
                            state = (state as SeekState.Content).copy(currentLocation = RadarLocation(
                                    latitude = it.latitude.toFloat(),
                                    longitude = it.longitude.toFloat()
                            ))
                        }
                    }
                    .launchIn(viewModelScope)
        }
    }

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
                updatedLocations[event.device.address] = RadarLocation(
                        latitude = (event.message as Message.Location).latitude.toFloat(),
                        longitude = (event.message as Message.Location).longitude.toFloat()
                )

                state = (state as SeekState.Content).copy(locations = updatedLocations)
            }
        }
    }
}
