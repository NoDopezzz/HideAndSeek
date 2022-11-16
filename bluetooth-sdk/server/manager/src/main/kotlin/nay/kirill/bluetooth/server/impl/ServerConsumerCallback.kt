package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice
import nay.kirill.bluetooth.server.exceptions.ServerException

interface ServerConsumerCallback {

    fun onNewMessage(device: BluetoothDevice, message: String)

    fun onNewDeviceConnected(device: BluetoothDevice)

    fun onDeviceDisconnected(device: BluetoothDevice)

    fun onServerReady()

    fun onFailure(throwable: ServerException)

}