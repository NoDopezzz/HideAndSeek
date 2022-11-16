package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice

interface ServerConsumerCallback {

    fun onNewMessage(device: BluetoothDevice, message: String)

    fun onNewDeviceConnected(device: BluetoothDevice)

    fun onDeviceDisconnected(device: BluetoothDevice)

    fun onServerReady()

}