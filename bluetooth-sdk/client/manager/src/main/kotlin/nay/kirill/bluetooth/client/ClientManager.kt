package nay.kirill.bluetooth.client

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import nay.kirill.bluetooth.utils.CharacteristicConstants.CHARACTERISTIC_UUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.SERVICE_UUID
import no.nordicsemi.android.ble.BleManager

class ClientManager(
        appContext: Context,
        private val consumerCallback: ClientConsumerCallback
) : BleManager(appContext) {

    override fun getGattCallback() = object : BleManagerGattCallback() {

        private var characteristic: BluetoothGattCharacteristic? = null

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val service = gatt.getService(SERVICE_UUID) ?: return false

            characteristic = service.getCharacteristic(CHARACTERISTIC_UUID)
            val properties = characteristic?.properties ?: 0

            return (properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) &&
                    (properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0)
        }

        override fun onServicesInvalidated() {
            consumerCallback.onServiceInvalidated()
            characteristic = null
        }

        override fun initialize() {
            setNotificationCallback(characteristic).with { device, data ->
                if (data.value != null) {
                    val value = String(data.value!!, Charsets.UTF_8)
                    consumerCallback.onNewMessage(device, value)
                }
            }

            beginAtomicRequestQueue()
                    .add(enableNotifications(characteristic)
                            .fail { _: BluetoothDevice?, status: Int ->
                                consumerCallback.onSubscriptionFailed(IllegalStateException("Was not able to subscribe: $status"))
                                disconnect().enqueue()
                            }
                    )
                    .done { device ->
                        consumerCallback.onSubscriptionSuccess(device)
                    }
                    .enqueue()
        }
    }
}