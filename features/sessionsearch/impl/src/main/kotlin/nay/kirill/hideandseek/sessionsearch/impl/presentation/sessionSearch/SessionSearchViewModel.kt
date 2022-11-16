package nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.bluetooth.scanner.api.BluetoothScanner
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting.WaitingArgs

internal class SessionSearchViewModel(
        converter: SessionSearchStateConverter,
        private val navigation: SessionSearchNavigation,
        private val bluetoothScanner: BluetoothScanner,
        private val clientEventCallback: ClientEventCallback
) : BaseEffectViewModel<SessionSearchState, SessionSearchUiState, SessionSearchEffect>(
        converter = converter,
        initialState = SessionSearchState(ContentEvent.Loading())
) {

    private var scanningJob: Job? = null

    fun back() = navigation.back()

    fun init() {
        clientEventCallback.result
                .onEach { event ->
                    when (event) {
                        is ClientEvent.OnFailure -> {
                            state = state.copy(
                                    devicesEvent = ContentEvent.Error(event.throwable),
                                    deviceAddressToConnect = null
                            )
                        }
                        is ClientEvent.ConnectionSuccess -> {
                            navigation.openWaiting(args = WaitingArgs(event.device))
                        }
                        else -> Unit
                    }
                }
                .launchIn(viewModelScope)
    }

    fun startScanning() {
        state = state.copy(devicesEvent = ContentEvent.Loading())

        scanningJob?.cancel()
        scanningJob = viewModelScope.launch {

            bluetoothScanner.getScannedDevicesFlow()
                    .collect { result ->
                        result
                                .onSuccess { devices ->
                                    state = state.copy(devicesEvent = ContentEvent.Success(devices))
                                }
                                .onFailure {
                                    state = state.copy(devicesEvent = ContentEvent.Error(it))
                                }
                    }

        }
    }

    fun onConnect(address: String) {
        scanningJob?.cancel()

        state = state.copy(deviceAddressToConnect = address)
        state.devicesEvent.onSuccess {
            data.firstOrNull { it.address == address }?.let {
                _effect.trySend(SessionSearchEffect.StartService(device = it))
            }
        }
    }

}
