package nay.kirill.bluetooth.scanner.impl

import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import nay.kirill.bluetooth.scanner.api.BluetoothScanner
import nay.kirill.bluetooth.scanner.api.BluetoothScannerException
import nay.kirill.bluetooth.scanner.api.ScannedDevice
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings

internal class BluetoothScannerImpl : BluetoothScanner {

    private val scanSettings by lazy {
        ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(4000)
                .setUseHardwareBatchingIfSupported(true)
                .build()
    }

    override suspend fun getScannedDevicesFlow(): Flow<Result<List<ScannedDevice>>> {
        try {
            return callbackFlow {
                val callback = object: ScanCallback() {

                    override fun onScanResult(callbackType: Int, result: ScanResult) {
                        super.onScanResult(callbackType, result)

                        channel.close()
                    }

                    override fun onScanFailed(errorCode: Int) {
                        super.onScanFailed(errorCode)

                        trySend(Result.failure(BluetoothScannerException(
                                "Failed to scan bluetooth devices. Error code"
                        )))
                        channel.close()
                    }

                    override fun onBatchScanResults(results: MutableList<ScanResult>) {
                        super.onBatchScanResults(results)

                        trySend(Result.success(value = results.map { ScannedDevice(it) }))
                    }
                }

                BluetoothLeScannerCompat.getScanner().startScan(null, scanSettings, callback)

                awaitClose { BluetoothLeScannerCompat.getScanner().stopScan(callback) }
            }
        } catch (e: Throwable) {
            return flowOf(Result.failure(e))
        }
    }

}