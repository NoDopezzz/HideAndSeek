package nay.kirill.location.huawei

import android.content.Context
import android.location.Location
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationCallback
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import nay.kirill.location.api.LocationManager

internal class LocationManagerHuaweiImpl(context: Context) : LocationManager {

    private val fusedLocationClient = FusedLocationProviderClient(context)

    override fun getLocationFlow(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.create()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 3000
        }

        val callback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                trySend(locationResult?.lastLocation)
            }

        }

        fusedLocationClient.requestLocationUpdates(locationRequest, callback, null)

        awaitClose { fusedLocationClient.removeLocationUpdates(callback) }
    }
            .filterNotNull()
}