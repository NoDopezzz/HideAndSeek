package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.content.Context
import android.util.Log
import nay.kirill.bluetooth.utils.CharacteristicConstants
import no.nordicsemi.android.ble.BleServerManager
import no.nordicsemi.android.ble.observer.ServerObserver
import java.nio.charset.StandardCharsets

class ServerManager(
        private val context: Context
) : BleServerManager(context), ServerObserver {

    private val gattCharacteristic = sharedCharacteristic(
            CharacteristicConstants.appUUID,
            BluetoothGattCharacteristic.PROPERTY_READ
                    or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM,
            descriptor(
                    CharacteristicConstants.characteristicUUID,
                    BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED_MITM
                            or BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED_MITM, byteArrayOf(0, 0)
            ),
            description("A characteristic to be read", false) // descriptors
    )

    private val gattService = service(
            CharacteristicConstants.appUUID,
            gattCharacteristic
    )

    private val serverConnections = mutableMapOf<String, DeviceConnectionManager>()

    override fun initializeServer(): MutableList<BluetoothGattService> {
        setServerObserver(this)

        return mutableListOf(gattService)
    }

    override fun onServerReady() {

    }

    override fun onDeviceConnectedToServer(device: BluetoothDevice) {
        Log.i("ServerManager", "On new device connected: ${device.name}")

        serverConnections[device.address] = DeviceConnectionManager(context)
                .apply {
                    useServer(this@ServerManager)
                    connect(device).enqueue()
                }
    }

    override fun onDeviceDisconnectedFromServer(device: BluetoothDevice) {
        serverConnections.remove(device.address)?.close()
    }

    fun sendMessage(message: String, deviceId: String?) {
        val bytes = message.toByteArray(StandardCharsets.UTF_8)
        if (deviceId == null) {
            serverConnections.forEach { it.value.sendMessage(bytes, gattCharacteristic) }
        } else {
            serverConnections[deviceId]?.sendMessage(bytes, gattCharacteristic)
        }
    }

}
