package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import no.nordicsemi.android.ble.BleManager

class DeviceConnectionManager(context: Context) : BleManager(context) {

    override fun getGattCallback(): BleManagerGattCallback = GattCallback()

    private inner class GattCallback : BleManagerGattCallback() {

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean = true

        override fun onServicesInvalidated() {
            /* Do not need any service from connected device */
        }

    }

    fun sendMessage(value: ByteArray, characteristic: BluetoothGattCharacteristic) {
        sendNotification(characteristic, value).enqueue()
    }

}