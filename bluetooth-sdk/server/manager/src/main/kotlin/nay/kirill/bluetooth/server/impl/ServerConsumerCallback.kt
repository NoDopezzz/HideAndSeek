package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice

interface ServerConsumerCallback {

    fun onNewDeviceConnected(device: BluetoothDevice)

    fun onDeviceDisconnected(device: BluetoothDevice)

    fun onServerReady()

}