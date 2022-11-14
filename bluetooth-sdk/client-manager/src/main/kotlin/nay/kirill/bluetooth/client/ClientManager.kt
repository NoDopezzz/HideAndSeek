package nay.kirill.bluetooth.client

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import nay.kirill.bluetooth.utils.CharacteristicConstants.appUUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.characteristicUUID
import no.nordicsemi.android.ble.BleManager

class ClientManager(
        appContext: Context
) : BleManager(appContext) {

    override fun getGattCallback() = object : BleManagerGattCallback() {

        private var characteristic: BluetoothGattCharacteristic? = null

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val service = gatt.getService(appUUID) ?: return false

            characteristic = service.getCharacteristic(characteristicUUID)
            val properties = characteristic?.properties ?: 0

            return (properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) &&
                    (properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0)
        }

        override fun onServicesInvalidated() {
            characteristic = null
        }

        override fun initialize() {
            setNotificationCallback(characteristic).with { _, data ->
                if (data.value != null) {
                    val value = String(data.value!!, Charsets.UTF_8)
                    TODO()
                }
            }

            beginAtomicRequestQueue()
                    .add(enableNotifications(characteristic)
                            .fail { _: BluetoothDevice?, status: Int ->
                                log(Log.ERROR, "Could not subscribe: $status")
                                disconnect().enqueue()
                            }
                    )
                    .done {
                        log(Log.INFO, "Target initialized")
                    }
                    .enqueue()
        }

    }
}