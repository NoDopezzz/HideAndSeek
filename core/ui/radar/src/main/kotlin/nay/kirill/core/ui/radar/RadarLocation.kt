package nay.kirill.core.ui.radar

data class RadarLocation(
        val latitude: Double,
        val longitude: Double,
        val isNear: Boolean = false
)