package nay.kirill.hideandseek.client.impl.presentation.waiting

import android.bluetooth.BluetoothDevice

internal sealed interface WaitingState {

    data class Content(val serverDevice: BluetoothDevice?) : WaitingState

    object Error : WaitingState

}

internal sealed interface WaitingUiState {

    data class Content(val deviceName: String) : WaitingUiState

    object Error : WaitingUiState

}