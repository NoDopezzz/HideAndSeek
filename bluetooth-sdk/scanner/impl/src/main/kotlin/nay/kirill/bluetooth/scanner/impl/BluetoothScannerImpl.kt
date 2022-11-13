package nay.kirill.bluetooth.scanner.impl

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import nay.kirill.bluetooth.scanner.api.BluetoothScanner
import nay.kirill.bluetooth.scanner.api.BluetoothScannerException
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class BluetoothScannerImpl(
        private val context: Context
) : BluetoothScanner {

    private val scanSettings by lazy {
        ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build()
    }

    override suspend fun getScannedDevices(): Result<List<BluetoothDevice>> {
        try {
            checkPermission()
            return suspendCoroutine { continuation ->
                BluetoothLeScannerCompat.getScanner().startScan(null, scanSettings, object: ScanCallback() {

                    override fun onScanResult(callbackType: Int, result: ScanResult) {
                        super.onScanResult(callbackType, result)

                        continuation.resume(Result.failure(BluetoothScannerException(
                                "Got scan result"
                        )))
                    }

                    override fun onScanFailed(errorCode: Int) {
                        super.onScanFailed(errorCode)

                        continuation.resume(Result.failure(BluetoothScannerException(
                                "Failed to scan bluetooth devices. Error code $errorCode"
                        )))
                    }

                    override fun onBatchScanResults(results: MutableList<ScanResult>) {
                        super.onBatchScanResults(results)

                        continuation.resume(Result.success(value = results.map { it.device }))
                    }
                })
            }
        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw BluetoothScannerException(
                    "BLUETOOTH_CONNECT permission is not granted!"
            )
        }
    }
}