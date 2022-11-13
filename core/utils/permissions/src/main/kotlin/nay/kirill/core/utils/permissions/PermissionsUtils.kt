package nay.kirill.core.utils.permissions

import android.Manifest
import android.os.Build

object PermissionsUtils {

    val bluetoothScanningPermissions: Array<String>
        get() {
            val permissions = mutableListOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
                permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            }

            return permissions.toTypedArray()
        }

}
