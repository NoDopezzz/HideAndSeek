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

    fun onPhoto() {
        state = contentState { copy(isScanning = true) }
    }

    fun onLocation() {
        state = contentState { copy(isScanning = false) }
    }

    fun onScan(address: String) {
        (state as? SeekState.Content)?.apply {
            if (locations.containsKey(address)) {
                viewModelScope.launch {
                    updateLocation(locations = locations.toMutableMap().apply { remove(address) })
                    state = contentState { copy(isScanning = false) }
                }
                // TODO notify user and navigate to found screen
            } else {
                _effect.trySend(SeekEffect.ShowToast("Не удалось прочитать QR code"))
            }

        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun fetchCurrentLocation() {
        viewModelScope.launch {
            locationManager.getLocationFlow(interval = 500)
                    .onEach { currentLocation ->
                        withContext(Dispatchers.IO) {
                            updateLocation(currentLocation)
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
            event is ServerEvent.OnDeviceDisconnected -> {
                val updatedLocations = (state as SeekState.Content).locations.toMutableMap().apply {
                    remove(event.device.address)
                }
                updateLocation(locations = updatedLocations)
            }
            event is ServerEvent.OnMinorException -> {
                _effect.trySend(SeekEffect.ShowToast(message = "Произошла ошибка ${event.throwable.message}"))
            }
            event is ServerEvent.OnNewMessage && event.message is Message.Location && state is SeekState.Content -> {
                val updatedLocations = (state as SeekState.Content).locations.toMutableMap()
                val location = (event.message as Message.Location)

                updatedLocations[event.device.address] = RadarLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                )

                updateLocation(locations = updatedLocations)
            }
        }
    }

    private suspend fun updateLocation(
            currentLocation: Location? = null,
            locations: Map<String, RadarLocation>? = null
    ) {
        val realCurrentLocation = currentLocation ?: (state as? SeekState.Content)?.currentLocation ?: return
        val realLocations = locations ?: (state as? SeekState.Content)?.locations ?: return

        realLocations
                .mapValues { targetLocation ->
                    val isNear = checkLocationDistance(realCurrentLocation, targetLocation.value)
                    if (targetLocation.value.isNear != isNear) {
                        if (isNear) _effect.trySend(SeekEffect.Vibrate)
                        serverMessageCallback.setResult(
                                ServerMessage.WriteCharacteristic(
                                        message = Message.IsNear(isNear),
                                        deviceAddress = targetLocation.key
                                )
                        )
                    }

                    targetLocation.value.copy(isNear = isNear)
                }
                .also { mappedLocations ->
                    withContext(Dispatchers.Main) {
                        state = contentState {
                            copy(
                                    currentLocation = currentLocation,
                                    locations = mappedLocations,
                                    isScanning = isScanning && mappedLocations.any { it.value.isNear }
                            )
                        }
                    }
                }
    }

    private fun checkLocationDistance(
            currentLocation: Location,
            targetLocation: RadarLocation,
    ): Boolean = try {
        val results = FloatArray(2)
        Location.distanceBetween(
                currentLocation.latitude,
                currentLocation.longitude,
                targetLocation.latitude,
                targetLocation.longitude,
                results
        )

        results[0] < 5
    } catch (e: Throwable) {
        false
    }

    private fun contentState(update: SeekState.Content.() -> SeekState): SeekState {
        return (state as? SeekState.Content)?.let {
            it.update()
        }
            ?: state
    }

}
