package nay.kirill.hideandseek.client.impl.presentation.hide

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        initialState = HideState.Content(deviceAddress = null)
) {

    init {
        clientEventCallback.result
                .onEach(::handleEvent)
                .launchIn(viewModelScope)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun init(bluetoothManager: BluetoothManager) {
        state = HideState.Content(deviceAddress = bluetoothManager.adapter.address)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun startLocationUpdating() {
        locationManager.getLocationFlow()
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
        when (event) {
            is ClientEvent.OnFailure -> {
                _effect.trySend(HideEffect.Error)
                state = HideState.Error
            }
            else -> Unit
        }
    }

}
