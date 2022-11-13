package nay.kirill.core.utils.permissions

import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionsUtils {

    fun isLocationPermissionGranted(context: Context): Boolean = ContextCompat
            .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

}
