package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.content.Context
import nay.kirill.bluetooth.utils.CharacteristicConstants
import no.nordicsemi.android.ble.BleManager
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
                    or BluetoothGattCharacteristic.PROPERTY_WRITE
                    or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE,
            descriptor(CharacteristicConstants.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID,
                    BluetoothGattDescriptor.PERMISSION_READ
                            or BluetoothGattDescriptor.PERMISSION_WRITE, byteArrayOf(0, 0)),
            description("A characteristic to be read", true) // descriptors
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

    private inner class DeviceConnectionManager(
            context: Context
    ) : BleManager(context) {

        override fun getGattCallback(): BleManagerGattCallback = GattCallback()

        private inner class GattCallback : BleManagerGattCallback() {

            override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean = true

            override fun onServicesInvalidated() {
                /* Do not need any service from connected device */
            }

            override fun initialize() {
                setWriteCallback(gattCharacteristic).with {device, data ->
                    if (data.value != null) {
                        val value = String(data.value!!, Charsets.UTF_8)
                        consumerCallback.onNewMessage(device, value)
                    }
                }
            }

        }

        fun sendMessage(value: ByteArray, characteristic: BluetoothGattCharacteristic) {
            sendNotification(characteristic, value).enqueue()
        }

    }

}
