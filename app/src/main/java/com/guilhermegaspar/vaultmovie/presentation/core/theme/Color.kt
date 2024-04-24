package com.guilhermegaspar.vaultmovie.presentation.core.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

const val MinContrastOfPrimaryVsSurface = 3f

val Yellow800 = Color(0xFFF29F05)
val Red300 = Color(0xFFEA6D7E)

val NutrifitColors = darkColorScheme(
    primary = Yellow800,
    onPrimary = Color.Black,
    secondary = Yellow800,
    onSecondary = Color.Black,
    error = Red300,
    onError = Color.Black
)
