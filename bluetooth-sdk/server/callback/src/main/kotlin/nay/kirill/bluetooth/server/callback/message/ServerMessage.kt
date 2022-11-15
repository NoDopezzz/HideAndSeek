package nay.kirill.bluetooth.server.callback.message

/**
 * [ServerMessage] that send to ServerService to communicate with ServerManager
 */
sealed interface ServerMessage {

    data class WriteCharacteristic(val message: String, val deviceId: String) : ServerMessage

}
