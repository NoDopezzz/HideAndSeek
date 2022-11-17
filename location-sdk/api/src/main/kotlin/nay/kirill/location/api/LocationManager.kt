package nay.kirill.location.api

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.Flow

interface LocationManager {

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getLocationFlow() : Flow<Location>

}