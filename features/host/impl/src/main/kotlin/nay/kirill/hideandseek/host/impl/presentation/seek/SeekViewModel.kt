package nay.kirill.hideandseek.host.impl.presentation.seek

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nay.kirill.bluetooth.messages.Message
import nay.kirill.bluetooth.server.callback.event.ServerEvent
import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.bluetooth.server.callback.message.ServerMessage
import nay.kirill.bluetooth.server.callback.message.ServerMessageCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.core.ui.radar.RadarLocation
import nay.kirill.hideandseek.host.impl.presentation.HostNavigation
import nay.kirill.location.api.LocationManager

internal class SeekViewModel(
        args: SeekArgs,
        converter: SeekStateConverter,
        serverEventCallback: ServerEventCallback,
        private val navigation: HostNavigation,
        private val locationManager: LocationManager,
        private val serverMessageCallback: ServerMessageCallback
) : BaseEffectViewModel<SeekState, SeekUiState, SeekEffect>(
        converter = converter,
        initialState = SeekState.Content(
                locations = mapOf(),
                leftDevicesCount = args.connectedDeviceCount
        )
) {

    init {
        serverEventCallback.result
                .onEach(::handleEvent)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
    }

    fun back() = navigation.back()

    fun retry() = navigation.openHosting()

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun fetchCurrentLocation() {
        viewModelScope.launch {
            locationManager.getLocationFlow(interval = 500)
                    .onEach {
                        if (state is SeekState.Content) {
                            state = (state as SeekState.Content).copy(
                                    currentLocation = RadarLocation(
                                            latitude = it.latitude.toFloat(),
                                            longitude = it.longitude.toFloat()
                                    )
                            )
                        }
                    }
                    .launchIn(viewModelScope)
        }
    }

    private suspend fun handleEvent(event: ServerEvent) {
        when {
            event is ServerEvent.OnFatalException -> {
                _effect.trySend(SeekEffect.StopService)
                withContext(Dispatchers.Main) { state = SeekState.Error }
            }
            event is ServerEvent.OnDeviceDisconnected && event.deviceCount == 0 -> {
                _effect.trySend(SeekEffect.StopService)
                withContext(Dispatchers.Main) { state = SeekState.NoDevicesConnected }
            }
            event is ServerEvent.OnMinorException -> {
                _effect.trySend(SeekEffect.ShowToast(message = "Произошла ошибка ${event.throwable.message}"))
            }
            event is ServerEvent.OnNewMessage && event.message is Message.Location && state is SeekState.Content -> {
                val updatedLocations = (state as SeekState.Content).locations.toMutableMap()
                val location = (event.message as Message.Location)

                val isNear = (state as SeekState.Content).currentLocation
                        ?.let { currentLocation ->
                            checkLocationDistance(
                                    location.latitude,
                                    location.longitude,
                                    currentLocation.latitude.toDouble(),
                                    currentLocation.longitude.toDouble()
                            )
                        }
                    ?: false

                if (updatedLocations[event.device.address]?.isNear != isNear) {
                    if (isNear) _effect.trySend(SeekEffect.Vibrate)
                    serverMessageCallback.setResult(ServerMessage.WriteCharacteristic(Message.IsNear(isNear), event.device.address))
                }

                updatedLocations[event.device.address] = RadarLocation(
                        latitude = location.latitude.toFloat(),
                        longitude = location.longitude.toFloat(),
                        isNear = isNear
                )

                withContext(Dispatchers.Main) {
                    state = (state as SeekState.Content).copy(
                            locations = updatedLocations,
                            leftDevicesCount = event.deviceCount
                    )
                }
            }
        }
    }

    private fun checkLocationDistance(
            latitude1: Double,
            longitude1: Double,
            latitude2: Double,
            longitude2: Double
    ): Boolean = try {
        val results = FloatArray(2)
        Location.distanceBetween(
                latitude1,
                longitude1,
                latitude2,
                longitude2,
                results
        )

        results[0] < 5
    } catch (e: Throwable) {
        false
    }
}
