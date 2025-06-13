package ru.ttb220.ui.component

import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.ttb220.ui.R
import ru.ttb220.ui.theme.FindetTheme

internal data class SunAndMoonVisuals(
    val sky: Sky = DEFAULT_DAY_CLEAR_SKY,
    val thumb: Thumb = DEFAULT_THUMB,
    val stars: List<Star> = DEFAULT_STARS,
    val rays: List<Ray> = DEFAULT_RAYS,
    val frontClouds: List<Cloud> = DEFAULT_FRONT_CLOUDS,
    val backClouds: List<Cloud> = DEFAULT_BACK_CLOUDS,
) {
    data class Shadow(
        val offsetX: Dp,
        val offsetY: Dp,
        val blur: Dp,
        val spread: Dp,
        val color: Color,
    )

    data class Sky(
        val color: Color = DEFAULT_DAY_SKY_COLOR,
        val width: Dp = DEFAULT_WIDTH,
        val height: Dp = DEFAULT_HEIGHT,
        val innerShadowUp: Shadow = Shadow(
            0.dp,
            1.25.dp,
            1.6.dp,
            0.dp,
            DEFAULT_BLACK_SHADOW_COLOR
        ),
        val innerShadowUpDepth: Shadow = Shadow(
            offsetX = 0.dp,
            offsetY = 0.18.dp,
            blur = 2.32.dp,
            spread = 0.dp,
            color = DEFAULT_BLACK_SHADOW_COLOR
        ),
        val dropShadowDown: Shadow = Shadow(
            offsetX = 0.dp,
            offsetY = 0.71.dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_WHITE_SHADOW_COLOR
        ),
        val dropShadowUp: Shadow = Shadow(
            offsetX = 0.dp,
            offsetY = (-0.53).dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_BLACK_SHADOW_COLOR
        ),
    )

    data class Thumb(
        val dropShadowDownDepth: Shadow = Shadow(
            offsetX = 0.dp,
            offsetY = 0.71.dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_BLACK_SHADOW_COLOR
        ),
        val dropShadowDown: Shadow = Shadow(
            offsetX = 0.71.dp,
            offsetY = 1.25.dp,
            blur = 1.42.dp,
            spread = 0.dp,
            color = DEFAULT_BLACK_SHADOW_COLOR
        ),
        val sun: Sun = Sun(),
        val moon: Moon = Moon(),
    )

    data class Sun(
        val diameter: Dp = DEFAULT_THUMB_DIAMETER,
        val color: Color = DEFAULT_SUN_COLOR,
        val innerShadowUp: Shadow = Shadow(
            offsetX = 0.53.dp,
            offsetY = 0.71.dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_WHITE_SHADOW_COLOR
        ),
        val innerShadowDown: Shadow = Shadow(
            offsetX = 0.dp,
            offsetY = (-0.89).dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_BLACK_SHADOW_COLOR
        )
    )

    data class Moon(
        val moonBase: MoonBase = MoonBase(),
        val spots: List<Spot> = listOf(
            Spot(
                offsetX = 9.08.dp,
                offsetY = 3.21.dp,
                diameter = 3.03.dp
            ),
            Spot(
                offsetX = 3.74.dp,
                offsetY = 9.26.dp,
                diameter = 7.48.dp
            ),
            Spot(
                offsetX = 12.64.dp,
                offsetY = 9.26.dp,
                diameter = 4.81.dp
            )
        )
    )

    data class Spot(
        val offsetX: Dp,
        val offsetY: Dp,
        val diameter: Dp,
        val color: Color = DEFAULT_SPOT_COLOR,
        val innerShadowUp: Shadow = Shadow(
            offsetX = 0.dp,
            offsetY = 0.18.dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_BLACK_SHADOW_COLOR
        ),
    )

    data class MoonBase(
        val diameter: Dp = DEFAULT_THUMB_DIAMETER,
        val color: Color = DEFAULT_MOON_COLOR,
        val innerShadowUp: Shadow = Shadow(
            offsetX = 0.53.dp,
            offsetY = 0.71.dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_WHITE_SHADOW_COLOR
        ),
        val innerShadowDown: Shadow = Shadow(
            offsetX = 0.dp,
            offsetY = (-0.89).dp,
            blur = 0.71.dp,
            spread = 0.dp,
            color = DEFAULT_BLACK_SHADOW_COLOR
        )
    )

    data class Star(
        val offsetX: Dp,
        val offsetY: Dp,
        val size: Dp,
        val color: Color = DEFAULT_STAR_COLOR,
        @DrawableRes val drawableId: Int = R.drawable.star,
    )

    data class Ray(
        val diameter: Dp,
        val color: Color = DEFAULT_RAY_COLOR
    )

    data class Cloud(
        val offsetX: Dp,
        val offsetY: Dp,
        val diameterX: Dp,
        val diameterY: Dp,
        val color: Color,
    )

    @Suppress("Warnings")
    companion object {
        val DEFAULT_WIDTH = 65.71.dp
        val DEFAULT_HEIGHT = 26.dp
        val DEFAULT_THUMB_DIAMETER = 21.37.dp
        val DEFAULT_RAY_DIAMETER_STEP = 14.96.dp

        val DEFAULT_DAY_SKY_COLOR = Color(0xFF337BB3)
        val DEFAULT_NIGHT_SKY_COLOR = Color(0xFF1E1D2B)
        val DEFAULT_SUN_COLOR = Color(0xFFEFC529)
        val DEFAULT_MOON_COLOR = Color(0xFFC5CACD)
        val DEFAULT_SPOT_COLOR = Color(0xFF929BAF)
        val DEFAULT_STAR_COLOR = Color.White
        val DEFAULT_RAY_COLOR = Color(0x1AFFFFFF)
        val DEFAULT_FRONT_CLOUD_COLOR = Color(0xFFEFFEFF)
        val DEFAULT_BACK_CLOUD_COLOR = Color(0x99EFFEFF)

        //TODO: alpha should be 25% as in design
        val DEFAULT_BLACK_SHADOW_COLOR = Color(0x73000000)
        val DEFAULT_WHITE_SHADOW_COLOR = Color(0xF0FFFFFF)

        val DEFAULT_DAY_CLEAR_SKY = Sky()
        val DEFAULT_NIGHT_CLEAR_SKY = Sky().copy(color = DEFAULT_NIGHT_SKY_COLOR)
        val DEFAULT_SUN = Sun()
        val DEFAULT_MOON_WITH_DOTS = Moon()
        val DEFAULT_THUMB = Thumb()
        val DEFAULT_STARS = listOf(
            Star(
                offsetX = 34.9.dp,
                offsetY = 10.33.dp,
                size = 1.25.dp,
            ),
            Star(
                offsetX = 28.85.dp,
                offsetY = 13.dp,
                size = 1.07.dp,
            ),
            Star(
                offsetX = 32.05.dp,
                offsetY = 19.23.dp,
                size = 1.6.dp,
            ),
            Star(
                offsetX = 21.19.dp,
                offsetY = 18.16.dp,
                size = 0.53.dp,
            ),
            Star(
                offsetX = 22.97.dp,
                offsetY = 8.01.dp,
                size = 1.07.dp,
            ),
            Star(
                offsetX = 13.53.dp,
                offsetY = 15.85.dp,
                size = 0.53.dp,
            ),
            Star(
                7.84.dp,
                19.59.dp,
                1.25.dp,
            ),
            Star(
                offsetX = 5.34.dp,
                offsetY = 9.79.dp,
                size = 1.25.dp,
            ),
            Star(
                offsetX = 11.22.dp,
                offsetY = 2.85.dp,
                size = 1.78.dp,
            )
        )
        val DEFAULT_RAYS: List<Ray> = List(3) { index ->
            Ray(
                diameter = DEFAULT_THUMB_DIAMETER +
                        DEFAULT_RAY_DIAMETER_STEP * (index + 1),
            )
        }
        val DEFAULT_FRONT_CLOUDS: List<Cloud> = listOf(
            Cloud(
                offsetX = (-4.1).dp,
                offsetY = 18.88.dp,
                diameterX = 16.56.dp,
                diameterY = 16.56.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 43.1.dp,
                offsetY = 12.82.dp,
                diameterX = 30.45.dp,
                diameterY = 28.67.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 17.81.dp,
                offsetY = 18.88.dp,
                diameterX = 16.03.dp,
                diameterY = 15.49.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 8.73.dp,
                offsetY = 18.88.dp,
                diameterX = 16.03.dp,
                diameterY = 15.49.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 21.01.dp,
                offsetY = 17.1.dp,
                diameterX = 20.12.dp,
                diameterY = 19.23.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 31.16.dp,
                offsetY = 16.56.dp,
                diameterX = 22.62.dp,
                diameterY = 21.01.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 53.42.dp,
                offsetY = 2.32.dp,
                diameterX = 28.67.dp,
                diameterY = 24.4.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 51.64.dp,
                offsetY = (-9.97).dp,
                diameterX = 28.14.dp,
                diameterY = 27.78.dp,
                color = DEFAULT_FRONT_CLOUD_COLOR
            )
        )
        val DEFAULT_BACK_CLOUDS: List<Cloud> = listOf(
            Cloud(
                offsetX = (-5.88).dp,
                offsetY = 16.92.dp,
                diameterX = 16.56.dp,
                diameterY = 16.56.dp,
                color = DEFAULT_BACK_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 43.63.dp,
                offsetY = 4.63.dp,
                diameterX = 30.45.dp,
                diameterY = 28.67.dp,
                color = DEFAULT_BACK_CLOUD_COLOR,
            ),
            Cloud(
                offsetX = 16.03.dp,
                offsetY = 16.92.dp,
                diameterX = 16.03.dp,
                diameterY = 15.49.dp,
                color = DEFAULT_BACK_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 6.95.dp,
                offsetY = 16.92.dp,
                diameterX = 16.03.dp,
                diameterY = 15.49.dp,
                color = DEFAULT_BACK_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 19.23.dp,
                offsetY = 15.14.dp,
                diameterX = 20.12.dp,
                diameterY = 19.23.dp,
                color = DEFAULT_BACK_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 29.21.dp,
                offsetY = 11.93.dp,
                diameterX = 22.62.dp,
                diameterY = 21.01.dp,
                color = DEFAULT_BACK_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 51.64.dp,
                offsetY = 0.36.dp,
                diameterX = 28.67.dp,
                diameterY = 24.4.dp,
                color = DEFAULT_BACK_CLOUD_COLOR
            ),
            Cloud(
                offsetX = 46.66.dp,
                offsetY = (-12.11).dp,
                diameterX = 28.14.dp,
                diameterY = 27.78.dp,
                color = DEFAULT_BACK_CLOUD_COLOR
            )
        )
    }
}

internal data class SunAndMoonVisualsWOffset(
    internal val sunAndMoonVisuals: SunAndMoonVisuals = SunAndMoonVisuals(),
    val thumbOffsetX: Dp = 0.dp,
    val moonOffsetX: Dp = sunAndMoonVisuals.thumb.sun.diameter,
    val cloudsOffsetY: Dp = 0.dp,
    val starsOffsetY: Dp = sunAndMoonVisuals.sky.height * (-5),
) {
    @Suppress("Warnings")
    companion object {
        val DEFAULT_PRESSED_OFFSET: Dp = 3.56.dp
        val DEFAULT_PADDING: Dp = (SunAndMoonVisuals.DEFAULT_HEIGHT -
                SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER) / 2

        val DEFAULT_SUN_STATE = SunAndMoonVisualsWOffset(
            moonOffsetX = SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER,
            starsOffsetY = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * (-5)
        )
        val DEFAULT_SUN_PRESSED_STATE = SunAndMoonVisualsWOffset(
            thumbOffsetX = DEFAULT_PRESSED_OFFSET,
            moonOffsetX = SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER,
            starsOffsetY = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * (-5)
        )
        val DEFAULT_MOON_STATE = SunAndMoonVisualsWOffset(
            sunAndMoonVisuals = SunAndMoonVisuals(
                sky = SunAndMoonVisuals.Sky(
                    color = SunAndMoonVisuals.DEFAULT_NIGHT_SKY_COLOR
                )
            ),
            thumbOffsetX = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.width -
                    DEFAULT_PADDING * 2 -
                    SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER,
            moonOffsetX = 0.dp,
            cloudsOffsetY = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * 5,
            starsOffsetY = 0.dp
        )
        val DEFAULT_MOON_PRESSED_STATE = SunAndMoonVisualsWOffset(
            sunAndMoonVisuals = SunAndMoonVisuals(
                sky = SunAndMoonVisuals.Sky(
                    color = SunAndMoonVisuals.DEFAULT_NIGHT_SKY_COLOR
                )
            ),
            thumbOffsetX = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.width -
                    DEFAULT_PADDING * 2 -
                    SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER -
                    DEFAULT_PRESSED_OFFSET,
            moonOffsetX = 0.dp,
            cloudsOffsetY = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * 5,
            starsOffsetY = 0.dp
        )
        val DEFAULT_STATE = DEFAULT_SUN_STATE
    }
}

class SunAndMoonSwitchState(
    initialThumbState: ThumbState,
    val onStateChanged: (ThumbState) -> Unit
) {
    sealed interface ThumbState {
        data object Sun : ThumbState
        data object SunPressed : ThumbState
        data object Moon : ThumbState
        data object MoonPressed : ThumbState
    }

    internal var sunAndMoonVisualsWOffset: SunAndMoonVisualsWOffset by mutableStateOf(
        SunAndMoonVisualsWOffset.DEFAULT_STATE
    )
    private var thumbState by mutableStateOf(initialThumbState)

    init {
        sunAndMoonVisualsWOffset = when (thumbState) {
            ThumbState.Moon -> SunAndMoonVisualsWOffset.DEFAULT_MOON_STATE

            ThumbState.MoonPressed -> SunAndMoonVisualsWOffset.DEFAULT_MOON_PRESSED_STATE

            ThumbState.Sun -> SunAndMoonVisualsWOffset.DEFAULT_SUN_STATE

            ThumbState.SunPressed -> SunAndMoonVisualsWOffset.DEFAULT_SUN_PRESSED_STATE
        }
    }

    private suspend fun animateTo(
        target: SunAndMoonVisualsWOffset,
    ) = coroutineScope {
        val start = sunAndMoonVisualsWOffset
        val moonOffsetX = Animatable(start.moonOffsetX.value)
        val cloudsOffsetY = Animatable(start.cloudsOffsetY.value)
        val starsOffsetY = Animatable(start.starsOffsetY.value)
        val thumbOffsetX = Animatable(start.thumbOffsetX.value)
        val skyColor = Animatable(start.sunAndMoonVisuals.sky.color)

        launch {
            moonOffsetX.animateTo(
                targetValue = target.moonOffsetX.value,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            cloudsOffsetY.animateTo(
                targetValue = target.cloudsOffsetY.value,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            starsOffsetY.animateTo(
                targetValue = target.starsOffsetY.value,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            thumbOffsetX.animateTo(
                targetValue = target.thumbOffsetX.value,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            skyColor.animateTo(
                targetValue = target.sunAndMoonVisuals.sky.color,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                ),
                block = {
                    sunAndMoonVisualsWOffset = target.copy(
                        sunAndMoonVisuals = target.sunAndMoonVisuals.copy(
                            sky = target.sunAndMoonVisuals.sky.copy(color = skyColor.value)
                        ),
                        thumbOffsetX = thumbOffsetX.value.dp,
                        moonOffsetX = moonOffsetX.value.dp,
                        cloudsOffsetY = cloudsOffsetY.value.dp,
                        starsOffsetY = starsOffsetY.value.dp
                    )
                }
            )
        }
    }

    suspend fun onClick() {
        val startTime = System.currentTimeMillis()
        Log.d(TAG, "onClick started, thumbState = $thumbState")
        when (thumbState) {
            ThumbState.Moon -> animateTo(SunAndMoonVisualsWOffset.DEFAULT_SUN_STATE)
            ThumbState.MoonPressed -> animateTo(SunAndMoonVisualsWOffset.DEFAULT_SUN_STATE)
            ThumbState.Sun -> animateTo(SunAndMoonVisualsWOffset.DEFAULT_MOON_STATE)
            ThumbState.SunPressed -> animateTo(SunAndMoonVisualsWOffset.DEFAULT_MOON_STATE)
        }
        thumbState = when (thumbState) {
            ThumbState.Moon -> ThumbState.Sun
            ThumbState.MoonPressed -> ThumbState.Sun
            ThumbState.Sun -> ThumbState.Moon
            ThumbState.SunPressed -> ThumbState.Moon
        }
        onStateChanged(thumbState)
        val duration = System.currentTimeMillis() - startTime
        Log.d(TAG, "onClick completed in $duration ms, new thumbState = $thumbState")
    }

    companion object {
        private const val TAG = "SunAndMoonSwitch"
    }
}

@Composable
fun SunAndMoonSwitch(
    sunAndMoonSwitchState: SunAndMoonSwitchState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    SunAndMoonSwitch(
        sunAndMoonVisualsWOffset = sunAndMoonSwitchState.sunAndMoonVisualsWOffset,
        modifier = modifier,
        onThumbTap = {
            scope.launch {
                sunAndMoonSwitchState.onClick()
            }
        }
    )
}

@Composable
private fun SunAndMoonSwitch(
    sunAndMoonVisualsWOffset: SunAndMoonVisualsWOffset,
    modifier: Modifier = Modifier,
    onThumbTap: () -> Unit = { },
) {
    val padding = (sunAndMoonVisualsWOffset.sunAndMoonVisuals.sky.height -
            sunAndMoonVisualsWOffset.sunAndMoonVisuals.thumb.sun.diameter) / 2
    val density = LocalDensity.current
    val raysOffset = Offset(
        x = with(density) {
            (padding + SunAndMoonVisuals.DEFAULT_SUN.diameter / 2 + sunAndMoonVisualsWOffset.thumbOffsetX).toPx()
        },
        y = with(density) {
            (SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height / 2).toPx()
        }
    )
    Box {
        Sky(
            sky = sunAndMoonVisualsWOffset.sunAndMoonVisuals.sky,
            frontClouds = sunAndMoonVisualsWOffset.sunAndMoonVisuals.frontClouds,
            backClouds = sunAndMoonVisualsWOffset.sunAndMoonVisuals.backClouds,
            cloudsOffsetY = sunAndMoonVisualsWOffset.cloudsOffsetY,
            stars = sunAndMoonVisualsWOffset.sunAndMoonVisuals.stars,
            starsOffsetY = sunAndMoonVisualsWOffset.starsOffsetY,
            modifier = modifier
                .rays(
                    center = raysOffset,
                    rays = sunAndMoonVisualsWOffset.sunAndMoonVisuals.rays,
                    RoundedCornerShape(200.dp)
                )
        )
        Thumb(
            thumb = sunAndMoonVisualsWOffset.sunAndMoonVisuals.thumb,
            moonOffsetX = sunAndMoonVisualsWOffset.moonOffsetX,
            modifier = Modifier
                .offset(
                    x = padding + sunAndMoonVisualsWOffset.thumbOffsetX,
                    y = padding
                )
                .pointerInput(onThumbTap) {
                    detectTapGestures(onTap = {
                        onThumbTap()
                    })
                }
        )
    }
}

private fun Modifier.innerShadow(
    shape: Shape,
    shadow: SunAndMoonVisuals.Shadow
) = this.innerShadow(
    shape = shape,
    color = shadow.color,
    blur = shadow.blur,
    offsetX = shadow.offsetX,
    offsetY = shadow.offsetY,
    spread = shadow.spread
)

private fun Modifier.dropShadow(
    shape: Shape,
    shadow: SunAndMoonVisuals.Shadow
) = this.dropShadow(
    shape = shape,
    color = shadow.color,
    blur = shadow.blur,
    offsetX = shadow.offsetX,
    offsetY = shadow.offsetY,
    spread = shadow.spread,
)

@Composable
private fun Sky(
    sky: SunAndMoonVisuals.Sky,
    frontClouds: List<SunAndMoonVisuals.Cloud>,
    backClouds: List<SunAndMoonVisuals.Cloud>,
    cloudsOffsetY: Dp,
    stars: List<SunAndMoonVisuals.Star>,
    starsOffsetY: Dp,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(
        Modifier
            .size(
                width = sky.width,
                height = sky.height
            )
            .dropShadow(
                RoundedCornerShape(200.dp),
                sky.dropShadowDown
            )
            .dropShadow(
                RoundedCornerShape(200.dp),
                sky.dropShadowUp
            )
            .background(sky.color, RoundedCornerShape(200.dp))
            .innerShadow(
                RoundedCornerShape(200.dp),
                sky.innerShadowUp
            )
            .innerShadow(
                RoundedCornerShape(200.dp),
                sky.innerShadowUpDepth
            )
            .clouds(
                shape = RoundedCornerShape(200.dp),
                offset = Offset(0f, cloudsOffsetY.value),
                clouds = frontClouds
            )
            .clouds(
                shape = RoundedCornerShape(200.dp),
                offset = Offset(0f, cloudsOffsetY.value),
                clouds = backClouds
            )
            .stars(
                shape = RoundedCornerShape(200.dp),
                offset = Offset(0f, starsOffsetY.value),
                context = context,
                stars = stars
            )
            .then(modifier)
    )
}

@Composable
private fun Sun(
    sun: SunAndMoonVisuals.Sun,
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier
            .size(sun.diameter)
            .background(sun.color, CircleShape)
            .innerShadow(
                CircleShape,
                sun.innerShadowUp
            )
            .innerShadow(
                CircleShape,
                sun.innerShadowDown
            )
    )
}

@Composable
private fun Moon(
    moon: SunAndMoonVisuals.Moon,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .size(moon.moonBase.diameter)
            .background(moon.moonBase.color, CircleShape)
            .innerShadow(
                CircleShape,
                moon.moonBase.innerShadowUp
            )
            .innerShadow(
                CircleShape,
                moon.moonBase.innerShadowDown
            )
    ) {
        moon.spots.forEach { spot ->
            Box(
                Modifier
                    .offset(spot.offsetX, spot.offsetY)
                    .size(spot.diameter)
                    .background(spot.color, CircleShape)
                    .innerShadow(
                        CircleShape,
                        spot.innerShadowUp
                    )
            )
        }
    }
}

@Composable
private fun Thumb(
    thumb: SunAndMoonVisuals.Thumb,
    moonOffsetX: Dp,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Box(
            Modifier
                .size(thumb.sun.diameter)
                .dropShadow(
                    CircleShape,
                    thumb.dropShadowDown
                )
                .dropShadow(
                    CircleShape,
                    thumb.dropShadowDownDepth
                )
        ) {
            Sun(thumb.sun)
        }
        Box(
            Modifier
                .size(thumb.sun.diameter)
                .clip(CircleShape)
        ) {
            Moon(
                thumb.moon,
                Modifier.offset(x = moonOffsetX)
            )
        }
    }
}

private fun Modifier.rays(
    center: Offset,
    rays: List<SunAndMoonVisuals.Ray>,
    shape: Shape
) = this.drawBehind {
    val paint = Paint()

    val outline = shape.createOutline(size, layoutDirection, this)
    val path = Path().apply {
        when (outline) {
            is Outline.Generic -> addPath(outline.path)
            is Outline.Rectangle -> addRect(outline.rect)
            is Outline.Rounded -> addRoundRect(outline.roundRect)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.clipPath(path)

        rays.forEach { ray ->
            paint.color = ray.color
            canvas.drawCircle(
                center = center,
                radius = ray.diameter.toPx() / 2,
                paint = paint
            )
        }

        canvas.restore()
    }
}

private fun Modifier.clouds(
    shape: Shape,
    offset: Offset,
    clouds: List<SunAndMoonVisuals.Cloud>
) = this.drawWithContent {
    drawContent()
    val outline = shape.createOutline(size, layoutDirection, this)
    val path = Path().apply {
        when (outline) {
            is Outline.Generic -> addPath(outline.path)
            is Outline.Rectangle -> addRect(outline.rect)
            is Outline.Rounded -> addRoundRect(outline.roundRect)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.clipPath(path)

        val cloudPath = Path()
        clouds.forEach { cloud ->
            val left = offset.x + cloud.offsetX.toPx()
            val top = offset.y + cloud.offsetY.toPx()
            val right = left + cloud.diameterX.toPx()
            val bottom = top + cloud.diameterY.toPx()
            cloudPath.addOval(Rect(left, top, right, bottom))
        }
        val paint = Paint().apply {
            color = clouds.firstOrNull()?.color ?: Color.Transparent
            isAntiAlias = true
        }
        canvas.drawPath(cloudPath, paint)

        canvas.restore()
    }
}

private fun Modifier.stars(
    shape: Shape,
    offset: Offset,
    context: Context,
    stars: List<SunAndMoonVisuals.Star>
) = this.drawWithContent {
    drawContent()
    val outline = shape.createOutline(size, layoutDirection, this)
    val path = Path().apply {
        when (outline) {
            is Outline.Generic -> addPath(outline.path)
            is Outline.Rectangle -> addRect(outline.rect)
            is Outline.Rounded -> addRoundRect(outline.roundRect)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.clipPath(path)

        stars.forEach { star ->
            val drawable =
                ContextCompat.getDrawable(context, star.drawableId) ?: return@forEach

            val left = (star.offsetX.toPx() + offset.x).toInt()
            val top = (star.offsetY.toPx() + offset.y).toInt()
            val size = star.size.toPx().toInt()
            val right = left + size
            val bottom = top + size

            drawable.setBounds(left, top, right, bottom)

            drawable.setTint(star.color.toArgb())
            drawable.draw(canvas.nativeCanvas)
        }

        canvas.restore()
    }
}

@Preview
@Composable
private fun SunPreview() {
    FindetTheme {
        Sun(SunAndMoonVisuals.DEFAULT_SUN)
    }
}

@Preview
@Composable
private fun MoonPreview() {
    FindetTheme {
        Moon(SunAndMoonVisuals.DEFAULT_MOON_WITH_DOTS)
    }
}

@Preview
@Composable
private fun ThumbPreview() {
    val infiniteTransition = rememberInfiniteTransition()
    val moonOffsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = SunAndMoonVisuals.DEFAULT_SUN.diameter.value,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    FindetTheme {
        Box(
            Modifier
                .background(Color(0xFFD7DEE8))
                .padding(3.dp)
        ) {
            Thumb(
                SunAndMoonVisuals.DEFAULT_THUMB,
                moonOffsetX = moonOffsetX.dp
            )
        }
    }
}

@Preview
@Composable
private fun CloudsPreview() {
    Box(
        Modifier
            .size(
                SunAndMoonVisuals.DEFAULT_WIDTH,
                SunAndMoonVisuals.DEFAULT_HEIGHT
            )
            .background(SunAndMoonVisuals.DEFAULT_DAY_SKY_COLOR, RoundedCornerShape(200.dp))
            .clouds(
                shape = RoundedCornerShape(200.dp),
                offset = Offset.Zero,
                clouds = SunAndMoonVisuals.DEFAULT_BACK_CLOUDS
            )
            .clouds(
                shape = RoundedCornerShape(200.dp),
                offset = Offset.Zero,
                clouds = SunAndMoonVisuals.DEFAULT_FRONT_CLOUDS
            )
    )
}

@Preview
@Composable
private fun StarsPreview() {
    val context = LocalContext.current
    Box(
        Modifier
            .size(
                SunAndMoonVisuals.DEFAULT_WIDTH,
                SunAndMoonVisuals.DEFAULT_HEIGHT
            )
            .background(SunAndMoonVisuals.DEFAULT_NIGHT_SKY_COLOR, RoundedCornerShape(200.dp))
            .stars(
                RoundedCornerShape(200.dp),
                Offset.Zero,
                context,
                SunAndMoonVisuals.DEFAULT_STARS
            )
    )
}

@Preview
@Composable
private fun DaySkyPreview() {
    FindetTheme {
        Box(
            Modifier
                .background(Color(0xFFD7DEE8))
                .padding(3.dp)
        ) {
            Sky(
                SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY,
                SunAndMoonVisuals.DEFAULT_FRONT_CLOUDS,
                SunAndMoonVisuals.DEFAULT_BACK_CLOUDS,
                0.dp,
                SunAndMoonVisuals.DEFAULT_STARS,
                SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * -3
            )
        }
    }
}

@Preview
@Composable
private fun NightSkyPreview() {
    FindetTheme {
        Box(
            Modifier
                .background(Color(0xFFD7DEE8))
                .padding(3.dp)
        ) {
            Sky(
                SunAndMoonVisuals.DEFAULT_NIGHT_CLEAR_SKY,
                SunAndMoonVisuals.DEFAULT_FRONT_CLOUDS,
                SunAndMoonVisuals.DEFAULT_BACK_CLOUDS,
                SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * 5,
                SunAndMoonVisuals.DEFAULT_STARS,
                0.dp
            )
        }
    }
}

@Preview
@Composable
private fun RaysPreview() {
    FindetTheme {
        Box(
            Modifier
                .size(SunAndMoonVisuals.DEFAULT_WIDTH, SunAndMoonVisuals.DEFAULT_HEIGHT)
                .rays(
                    Offset(
                        x = 0f,
                        y = with(LocalDensity.current) {
                            (SunAndMoonVisuals.DEFAULT_HEIGHT / 2).toPx()
                        }
                    ),
                    SunAndMoonVisuals.DEFAULT_RAYS,
                    RoundedCornerShape(200.dp)
                )
        )
    }
}

@Preview
@Composable
private fun SunWithRaysPreview() {
    FindetTheme {
        val padding =
            (SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height - SunAndMoonVisuals.DEFAULT_SUN.diameter) / 2
        val raysOffset = Offset(
            x = with(LocalDensity.current) {
                (padding + SunAndMoonVisuals.DEFAULT_SUN.diameter / 2).toPx()
            },
            y = with(LocalDensity.current) {
                (SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height / 2).toPx()
            }
        )
        Box(
            modifier = Modifier
                .size(
                    width = SunAndMoonVisuals.DEFAULT_WIDTH,
                    height = SunAndMoonVisuals.DEFAULT_HEIGHT
                )
                .rays(
                    raysOffset,
                    SunAndMoonVisuals.DEFAULT_RAYS,
                    RoundedCornerShape(200.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            val modifier = Modifier
                .offset((SunAndMoonVisuals.DEFAULT_HEIGHT - SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER) / 2)
            Sun(
                SunAndMoonVisuals.DEFAULT_SUN,
                Modifier
                    .then(modifier)
            )
        }
    }
}

@Preview
@Composable
private fun SwitchAnimatedPreview() {
    FindetTheme {
        val infiniteTransition = rememberInfiniteTransition()
        val padding = (SunAndMoonVisuals.DEFAULT_HEIGHT -
                SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER) / 2
        val thumbMaxOffsetX = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.width -
                padding * 2 -
                SunAndMoonVisuals.DEFAULT_THUMB_DIAMETER
        val easing = FastOutSlowInEasing
        val moonOffsetX by infiniteTransition.animateFloat(
            initialValue = SunAndMoonVisuals.DEFAULT_SUN.diameter.value,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = easing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        val thumbOffsetX by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = thumbMaxOffsetX.value,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = easing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        val cloudOffsetY by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * 5).value,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = easing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        val starsOffsetY by infiniteTransition.animateFloat(
            initialValue = (SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.height * -5).value,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = easing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        val skyColor by infiniteTransition.animateColor(
            initialValue = SunAndMoonVisuals.DEFAULT_DAY_SKY_COLOR,
            targetValue = SunAndMoonVisuals.DEFAULT_NIGHT_SKY_COLOR,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = easing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        val sunAndMoonVisualsWOffset = SunAndMoonVisualsWOffset(
            sunAndMoonVisuals = SunAndMoonVisuals().copy(
                sky = SunAndMoonVisuals.DEFAULT_DAY_CLEAR_SKY.copy(
                    color = skyColor
                )
            ),
            thumbOffsetX = thumbOffsetX.dp,
            moonOffsetX = moonOffsetX.dp,
            cloudsOffsetY = cloudOffsetY.dp,
            starsOffsetY = starsOffsetY.dp
        )

        Box(
            Modifier
                .background(Color(0xFFD7DEE8))
                .padding(3.dp)
        ) {
            SunAndMoonSwitch(
                sunAndMoonVisualsWOffset,
            )
        }
    }
}

@Preview
@Composable
private fun SunAndMoonPreview() {
    FindetTheme {
        Column(
            Modifier
                .background(Color(0xFFD7DEE8))
                .padding(3.dp)
        ) {
            val switchState by remember {
                mutableStateOf(SunAndMoonSwitchState(
                    initialThumbState = SunAndMoonSwitchState.ThumbState.Sun,
                    onStateChanged = { }
                ))
            }
            SunAndMoonSwitch(
                switchState,
                Modifier
            )
            SunAndMoonSwitch(
                switchState,
                Modifier
            )
            SunAndMoonSwitch(
                switchState,
                Modifier
            )
        }
    }
}