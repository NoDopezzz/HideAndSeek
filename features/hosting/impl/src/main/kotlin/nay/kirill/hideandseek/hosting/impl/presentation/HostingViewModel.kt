package nay.kirill.hideandseek.hosting.impl.presentation

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.server.callback.event.ServerEvent
import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction

internal class HostingViewModel(
        converter: HostingStateConverter,
        private val navigation: HostingNavigation,
        private val serverServiceCallback: ServerEventCallback
) : BaseEffectViewModel<HostingState, HostingUiState, HostingEff>(
        converter = converter,
        initialState = HostingState(
                connectedDeviceEvent = ContentEvent.Loading(),
                hostDeviceName = ""
        )
) {

    @SuppressLint("MissingPermission")
    fun init(manager: BluetoothManager) {
        state = state.copy(hostDeviceName = manager.adapter.name)

        serverServiceCallback.result
                .onEach { event ->when (event) {
                        is ServerEvent.OnServerIsReady -> handleOnServerReady()
                        is ServerEvent.OnDeviceConnected -> handleNewDeviceConnected(device = event.device)
                        is ServerEvent.OnDeviceDisconnected -> handleDeviceDisconnected(device = event.device)
                        is ServerEvent.OnFatalException -> handleFatalServerFailure(error = event.throwable)
                        is ServerEvent.OnMinorException -> handleMinorException(error = event.throwable)
                        else -> Unit
                    }
                }
                .launchIn(viewModelScope)
    }

    fun onButtonClicked(buttonAction: ButtonAction) {
        when (buttonAction) {
            is ButtonAction.Start -> TODO()
            is ButtonAction.Retry -> _effect.trySend(HostingEff.RetryStartService)
            is ButtonAction.Back -> navigation.back()
        }
    }

    private fun handleOnServerReady() {
        state = state.copy(connectedDeviceEvent = ContentEvent.Loading())
    }

    private fun handleNewDeviceConnected(device: BluetoothDevice) {
        val devices = state.connectedDeviceEvent.data.orEmpty().plus(device)
        state = state.copy(connectedDeviceEvent = ContentEvent.Success(devices))
    }

    private fun handleDeviceDisconnected(device: BluetoothDevice) {
        val devices = state.connectedDeviceEvent.data.orEmpty().minus(device)
        state = state.copy(connectedDeviceEvent = ContentEvent.Success(devices))
    }

    private fun handleFatalServerFailure(error: Throwable) {
        state = state.copy(connectedDeviceEvent = ContentEvent.Error(error))
    }

    private fun handleMinorException(error: Throwable) {

    }

}
