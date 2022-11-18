package nay.kirill.core.ui.radar

data class RadarLocation(
        val latitude: Float,
        val longitude: Float,
        val isNear: Boolean = false
)