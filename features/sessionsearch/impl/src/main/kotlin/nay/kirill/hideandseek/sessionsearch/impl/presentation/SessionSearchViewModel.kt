package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nay.kirill.bluetooth.client.callback.ClientEvent
import nay.kirill.bluetooth.client.callback.ClientServiceCallback
import nay.kirill.bluetooth.scanner.api.BluetoothScanner
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.core.arch.ContentEvent

internal class SessionSearchViewModel(
        converter: SessionSearchStateConverter,
        private val navigation: SessionSearchNavigation,
        private val bluetoothScanner: BluetoothScanner,
        private val clientServiceCallback: ClientServiceCallback
) : BaseEffectViewModel<SessionSearchState, SessionSearchUiState, HostingEffect>(
        converter = converter,
        initialState = SessionSearchState(ContentEvent.Loading())
) {

    private var scanningJob: Job? = null

    fun back() = navigation.back()

    fun init() {
        clientServiceCallback.result
                .onEach { event ->
                    Log.i("SessionSearchViewModel", "New event: $event")

                    when (event) {
                        is ClientEvent.ServiceInvalidated -> {
                            state = state.copy(
                                    devicesEvent = ContentEvent.Error(IllegalStateException("Service got invalidated")),
                                    deviceAddressToConnect = null
                            )
                        }
                        is ClientEvent.SubscriptionResult -> {
                            event.result
                                    .onSuccess {
                                        state = state.copy(deviceAddressToConnect = null)
                                    }
                                    .onFailure {
                                        state = state.copy(devicesEvent = ContentEvent.Error(it), deviceAddressToConnect = null)
                                    }

                        }
                        is ClientEvent.OnNewMessage -> {
                            _effect.trySend(HostingEffect.NewMessageReceived(message = event.message))
                        }
                        else -> Unit
                    }
                }
                .launchIn(viewModelScope)
    }

    fun getDevices() {
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
                _effect.trySend(HostingEffect.StartService(device = it))
            }
        }
    }

}
