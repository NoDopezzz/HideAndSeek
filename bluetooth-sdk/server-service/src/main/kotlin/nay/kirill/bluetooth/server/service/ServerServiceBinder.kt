package nay.kirill.bluetooth.server.service

/**
 * Binder of [BleServerService].
 * Is used to communicate with connected devices via BLE
 */
interface ServerServiceBinder {

    /**
     * Method [sendMessage] sends message to connected devices
     * @param message is message that connected device will get
     * @param deviceId is id of target device. If [deviceId] is null
     * message will be sent to all connected devices
     */
    fun sendMessage(message: String, deviceId: String? = null)

}
