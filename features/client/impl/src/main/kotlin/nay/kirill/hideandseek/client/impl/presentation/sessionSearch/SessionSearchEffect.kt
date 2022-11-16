package nay.kirill.hideandseek.client.impl.presentation.sessionSearch

import android.bluetooth.BluetoothDevice

sealed interface SessionSearchEffect {

    data class StartService(val device: BluetoothDevice) : SessionSearchEffect

}