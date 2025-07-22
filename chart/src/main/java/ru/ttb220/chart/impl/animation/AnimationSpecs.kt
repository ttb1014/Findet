package ru.ttb220.chart.impl.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween

val DefaultAnimationSpec: AnimationSpec<Float> = tween(
    durationMillis = 1000,
    easing = DefaultEasing
)