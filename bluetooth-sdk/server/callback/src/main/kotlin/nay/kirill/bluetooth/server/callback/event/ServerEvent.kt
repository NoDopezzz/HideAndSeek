package nay.kirill.bluetooth.server.callback.event

import android.bluetooth.BluetoothDevice
import nay.kirill.bluetooth.server.exceptions.ServerException

/**
 * Events that BLE-server service produce.
 */
sealed interface ServerEvent {

    /**
     * [OnDeviceConnected] is sent when new device is connected to server
     */
    data class OnDeviceConnected(val device: BluetoothDevice) : ServerEvent

    /**
     * [OnDeviceConnected] is sent when device got disconnected from server
     */
    data class OnDeviceDisconnected(val device: BluetoothDevice) : ServerEvent

    /**
     * [OnServerIsReady] is sent when server is initialized and ready to use
     */
    object OnServerIsReady : ServerEvent

    /**
     * [OnFatalException] is sent when server connection exception is thrown.
     * If it happened ForegroundService is about to shutdown and you should reconnected to
     * server again
     */
    data class OnFatalException(val throwable: ServerException) : ServerEvent

    /**
     * [OnMinorException] defines exceptions while trying to update current state,
     * like write or read characteristics. It do not affect base server connection
     */
    data class OnMinorException(val throwable: ServerException) : ServerEvent

    /**
     * Receive [OnNewMessage] when got new message from connected BLE-client.
     * @param message is message that client sent
     * @param device is client that sent message
     */
    data class OnNewMessage(val message: String, val device: BluetoothDevice) : ServerEvent
}
