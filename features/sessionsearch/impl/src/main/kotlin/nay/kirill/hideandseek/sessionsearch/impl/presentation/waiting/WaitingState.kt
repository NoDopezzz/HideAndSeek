package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import android.bluetooth.BluetoothDevice

sealed interface WaitingState {

    data class Content(val serverDevice: BluetoothDevice?) : WaitingState

    object Error : WaitingState

}

sealed interface WaitingUiState {

    data class Content(val deviceName: String) : WaitingUiState

    object Error : WaitingUiState

}