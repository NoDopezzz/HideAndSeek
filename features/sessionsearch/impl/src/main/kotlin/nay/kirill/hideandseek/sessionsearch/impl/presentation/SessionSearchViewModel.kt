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
        state = state.copy(devices = ContentEvent.Loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val devicesResult = bluetoothScanner.getScannedDevices()
                devicesResult
                        .onSuccess { devices ->
                            state = state.copy(devices = ContentEvent.Success(devices))
                        }
                        .onFailure {
                            state = state.copy(devices = ContentEvent.Error(it))
                        }
            }
        }
    }

}
