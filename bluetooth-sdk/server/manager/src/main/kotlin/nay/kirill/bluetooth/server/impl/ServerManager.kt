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
        private val context: Context,
        private val consumerCallback: ServerConsumerCallback
) : BleServerManager(context), ServerObserver {

    private val gattCharacteristic = sharedCharacteristic(
            CharacteristicConstants.CHARACTERISTIC_UUID,
            BluetoothGattCharacteristic.PROPERTY_READ
                    or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ,
            description("A characteristic to be read", false) // descriptors
    )

    private val gattService = service(
            CharacteristicConstants.SERVICE_UUID,
            gattCharacteristic
    )

    private val serverConnections = mutableMapOf<String, DeviceConnectionManager>()

    override fun initializeServer(): MutableList<BluetoothGattService> {
        setServerObserver(this)

        return mutableListOf(gattService)
    }

    override fun onServerReady() {
        consumerCallback.onServerReady()
    }

    override fun onDeviceConnectedToServer(device: BluetoothDevice) {
        consumerCallback.onNewDeviceConnected(device)

        serverConnections[device.address] = DeviceConnectionManager(context)
                .apply {
                    useServer(this@ServerManager)
                    connect(device).enqueue()
                }
    }

    override fun onDeviceDisconnectedFromServer(device: BluetoothDevice) {
        consumerCallback.onDeviceDisconnected(device)

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
