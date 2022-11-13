package nay.kirill.bluetooth.scanner.api

import android.bluetooth.BluetoothDevice

interface BluetoothScanner {

    suspend fun getScannedDevices(): Result<List<BluetoothDevice>>

}
