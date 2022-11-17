package nay.kirill.hideandseek.client.impl.presentation.hide

import android.Manifest
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.hideandseek.client.impl.presentation.ClientNavigation

internal class HideViewModel(
        converter: HideStateConverter,
        clientEventCallback: ClientEventCallback,
        private val navigation: ClientNavigation
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
