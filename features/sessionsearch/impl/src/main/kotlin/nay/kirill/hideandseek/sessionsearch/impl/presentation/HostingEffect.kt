package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.bluetooth.BluetoothDevice

sealed interface HostingEffect {

    data class StartService(val device: BluetoothDevice) : HostingEffect

    data class NewMessageReceived(val message: String) : HostingEffect

}