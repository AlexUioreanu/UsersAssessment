package com.example.usersassessment.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val spaceXxs: Dp = 2.dp,
    val spaceXs: Dp = 4.dp,
    val spaceSm: Dp = 8.dp,
    val spaceMd: Dp = 16.dp,
    val spaceLg: Dp = 24.dp,
    val spaceXl: Dp = 32.dp,
    val spaceXxl: Dp = 48.dp,
    val space3Xl: Dp = 64.dp,
    val space4Xl: Dp = 128.dp,
)

val LocalDimensions = compositionLocalOf { Dimensions() }