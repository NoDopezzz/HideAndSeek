package nay.kirill.bluetooth.client

import android.bluetooth.BluetoothDevice

interface ClientConsumerCallback {

    fun onServiceInvalidated()

    fun onNewMessage(device: BluetoothDevice, message: String)

    fun onSubscriptionSuccess(device: BluetoothDevice)

    fun onSubscriptionFailed(error: Throwable)

}