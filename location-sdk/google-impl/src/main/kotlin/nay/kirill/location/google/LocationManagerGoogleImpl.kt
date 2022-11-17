package nay.kirill.location.google

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import nay.kirill.location.api.LocationManager

internal class LocationManagerGoogleImpl(context: Context) : LocationManager {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(context)

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun getLocationFlow(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .build()

        val callback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                trySend(locationResult.lastLocation)
            }

        }

        fusedLocationClient.requestLocationUpdates(locationRequest, callback, null)

        awaitClose { fusedLocationClient.removeLocationUpdates(callback) }
    }
            .filterNotNull()

}
