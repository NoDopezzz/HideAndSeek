package nay.kirill.core.utils.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionsUtils {

    fun checkBluetoothAdvertisePermission(context: Context): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.S
            || ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED

    fun checkBluetoothConnectPermission(context: Context): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.S
            || ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED

    val bluetoothScanningPermissions: Array<String>
        get() {
            val permissions = mutableListOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            }

            return permissions.toTypedArray()
        }

}
