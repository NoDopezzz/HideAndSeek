package nay.kirill.bluetooth.scanner.api

import kotlinx.coroutines.flow.Flow

interface BluetoothScanner {

    suspend fun getScannedDevicesFlow(): Flow<Result<List<ScannedDevice>>>

}
