package nay.kirill.bluetooth.client.callback

import android.bluetooth.BluetoothDevice

sealed interface ClientEvent {

    data class ConnectionResult(val connectResult: Result<BluetoothDevice>) : ClientEvent

    object ServiceInvalidated : ClientEvent

    data class OnNewMessage(val message: String) : ClientEvent

    data class SubscriptionResult(val result: Result<BluetoothDevice>) : ClientEvent

}