package nay.kirill.bluetooth.client.callback.event

import android.bluetooth.BluetoothDevice

/**
 * Events that BLE-client service produce.
 */
sealed interface ClientEvent {

    /**
     * [ConnectionResult] shows connection to server result.
     * @param connectResult is Result that defines connection status.
     * In success case contains BLE-server device
     */
    data class ConnectionResult(val connectResult: Result<BluetoothDevice>) : ClientEvent

    /**
     * [ServiceInvalidated] event often thrown when BLE-server is disconnected
     */
    object ServiceInvalidated : ClientEvent

    /**
     * [OnNewMessage] is emitted when client receives new message from server
     */
    data class OnNewMessage(val message: String) : ClientEvent

    /**
     * [SubscriptionResult] shown subscription to characteristics updates status.
     * In success case contains BLE-server device
     */
    data class SubscriptionResult(val result: Result<BluetoothDevice>) : ClientEvent

}