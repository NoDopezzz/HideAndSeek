package nay.kirill.core.ui.radar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.delay
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
        CenterPoint(
                modifier = Modifier
                        .align(Alignment.Center)
        )

        locations.forEach { location ->
            Point(
                    offset = LocationUtils.getX(
                            lat = location.latitude,
                            centerLat = centerLocation.latitude,
                            size = size
                    ) to LocationUtils.getY(
                            long = location.longitude,
                            centerLong = centerLocation.longitude,
                            size = size
                    ),
                    isNear = location.isNear
            )
        }
    }
}

@Composable
private fun Point(
        modifier: Modifier = Modifier,
        offset: Pair<Dp, Dp>,
        isNear: Boolean
) {
    val offsetAnimationX: Dp by animateDpAsState(
            targetValue = offset.first - 5.dp,
            animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val offsetAnimationY: Dp by animateDpAsState(
            targetValue = offset.second - 5.dp,
            animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Waves(
            waveSize = 10.dp,
            color = if (isNear) AppColors.TransparentRedUser else AppColors.TransparentGreenUser,
            modifier = modifier
                    .absoluteOffset(x = offsetAnimationX, y = offsetAnimationY)
    ) {
        Box(
                modifier = modifier
                        .size(5.dp)
                        .align(Alignment.Center)
                        .background(
                                color = if (isNear) AppColors.RedUser else AppColors.GreenUser,
                                shape = CircleShape
                        )
        )
    }

}

@Composable
private fun CenterPoint(
        modifier: Modifier
) {
    Waves(
            modifier = modifier,
            waveSize = 30.dp,
            color = AppColors.TransparentPrimary
    ) {
        Box(
                modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.Center)
                        .background(AppColors.Primary, shape = CircleShape)
        )
    }
}

@Composable
private fun Waves(
        modifier: Modifier = Modifier,
        waveSize: Dp,
        color: Color,
        factory: @Composable BoxScope.() -> Unit
) {
    val waves = listOf(
            remember { Animatable(0f) },
            remember { Animatable(0f) },
            remember { Animatable(0f) },
    )

    val animationSpec = infiniteRepeatable<Float>(
            animation = tween(4000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Restart,
    )

    waves.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 1000L)
            animatable.animateTo(targetValue = 1f, animationSpec = animationSpec)
        }
    }

    val dys = waves.map { it.value }

    Box(
            modifier = modifier
    ) {
        dys.forEach { dy ->
            Box(
                    Modifier
                            .size(waveSize)
                            .align(Alignment.Center)
                            .graphicsLayer {
                                scaleX = dy * 3 + 1
                                scaleY = dy * 3 + 1
                                alpha = 1 - dy
                            }
            ) {
                Box(
                        Modifier
                                .fillMaxSize()
                                .background(color = color, shape = CircleShape)
                )
            }
        }
        factory()
    }
}

@Preview
@Composable
private fun RadarPreview() {
    RadarView(
            centerLocation = RadarLocation(-8.777022F, 115.223076F),
            locations = listOf(RadarLocation(-8.777115F, 115.22314F)),
            size = 300.dp
    )
}