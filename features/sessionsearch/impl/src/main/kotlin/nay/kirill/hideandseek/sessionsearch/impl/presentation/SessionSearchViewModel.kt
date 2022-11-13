package nay.kirill.hideandseek.sessionsearch.impl.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nay.kirill.bluetooth.scanner.api.BluetoothScanner
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.core.arch.ContentEvent

internal class SessionSearchViewModel(
        converter: SessionSearchStateConverter,
        private val navigation: SessionSearchNavigation,
        private val bluetoothScanner: BluetoothScanner
) : BaseViewModel<SessionSearchState, SessionSearchUiState>(
        converter = converter,
        initialState = SessionSearchState(ContentEvent.Loading())
) {

    fun back() = navigation.back()

    fun getDevices() {
        state = state.copy(devicesEvent = ContentEvent.Loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
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
    }

}
