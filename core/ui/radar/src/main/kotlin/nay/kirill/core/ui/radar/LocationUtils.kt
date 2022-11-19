package nay.kirill.core.ui.radar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times

internal object LocationUtils {

    fun getX(lat: Float, centerLat: Float, size: Dp) = (lat - calculateStartLat(centerLat)) / RADIUS_LAT * size

    fun getY(long: Float, centerLong: Float, size: Dp) = (long - calculateStartLon(centerLong)) / RADIUS_LONG * size

    private fun calculateStartLat(centerLat: Float) = centerLat - RADIUS_LAT / 2F

    private fun calculateStartLon(centerLon: Float) = centerLon - RADIUS_LONG / 2F

    private const val RADIUS_LAT = 0.0005
    private const val RADIUS_LONG = 0.0005

}