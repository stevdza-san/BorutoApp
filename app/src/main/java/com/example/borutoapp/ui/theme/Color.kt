package com.example.borutoapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFDBDBDB)
val DarkGray = Color(0xFF2A2A2A)
val StarColor = Color(0xFFFFC940)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)


val Colors.welcomeScreenBackgroundColor
    @Composable
    get() = if (isLight) {
        Color.White
    } else {
        Color.Black
    }

val Colors.titleColor
    @Composable
    get() = if (isLight) DarkGray
            else LightGray

val Colors.descriptionColor
    @Composable
    get() = if (isLight) DarkGray.copy(alpha = 0.5f)
    else LightGray.copy(0.5f)

val Colors.activeIndicatorColor
    @Composable
    get() = if (isLight) Purple500
        else Purple700

val Colors.inactiveIndicatorColor
    @Composable
    get() = if (isLight) LightGray else DarkGray

val Colors.buttonBackgroundColor
    @Composable
    get() = if (isLight) Purple500
    else Purple700

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else Color.Black

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black
