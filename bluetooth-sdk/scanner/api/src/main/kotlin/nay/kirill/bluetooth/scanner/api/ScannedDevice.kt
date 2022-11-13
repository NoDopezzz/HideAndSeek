package nay.kirill.bluetooth.scanner.api

import android.annotation.SuppressLint
import no.nordicsemi.android.support.v18.scanner.ScanResult

data class ScannedDevice(
        val address: String,
        val name: String
) {

    @SuppressLint("MissingPermission")
    constructor(scanResult: ScanResult) : this(
            address = scanResult.device.address,
            name = scanResult.scanRecord?.deviceName
                ?: scanResult.device.name
                ?: scanResult.device.address
    )

}