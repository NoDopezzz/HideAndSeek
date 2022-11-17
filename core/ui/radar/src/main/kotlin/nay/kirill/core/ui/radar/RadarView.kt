package nay.kirill.core.ui.radar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import nay.kirill.core.compose.AppColors

@Composable
fun RadarView(
        centerLocation: RadarLocation,
        locations: List<RadarLocation>,
        size: Dp,
        modifier: Modifier = Modifier
) {
    Box(
            modifier = modifier
                    .size(size)
                    .border(BorderStroke(2.dp, AppColors.Primary), shape = CircleShape)
    ) {
        Box(
                modifier = Modifier
                        .align(Alignment.Center)
                        .size(20.dp)
                        .background(AppColors.Primary, shape = CircleShape)
        )

        val (startLat, startLong) = (centerLocation.latitude - LAT_IN_METER * 50F) to
                (centerLocation.longitude - LONG_IN_METER * 50F)

        locations.map { location ->
            (location.latitude - startLat) / LAT_IN_METER to
                    (location.longitude - startLong) / LONG_IN_METER
        }
                .map {
                    it.first * (size / RADIUS_IN_METERS) to it.second * (size / RADIUS_IN_METERS)
                }
                .forEach {
                    Box(
                            modifier = Modifier
                                    .padding(start = it.first - 5.dp, top = it.second - 5.dp)
                                    .size(10.dp)
                                    .background(AppColors.Secondary, shape = CircleShape)
                    )
                }
    }
}

private const val LAT_IN_METER = 0.016666666
private const val LONG_IN_METER = 0.016666666

private const val RADIUS_IN_METERS = 100

@Preview
@Composable
private fun RadarPreview() {
    RadarView(
            centerLocation = RadarLocation(-8.777022F, 115.223076F),
            locations = listOf(RadarLocation(-8.777115F, 115.22314F)),
            size = 300.dp
    )
}