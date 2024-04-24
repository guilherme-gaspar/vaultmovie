package com.guilhermegaspar.vaultmovie.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NutrifitTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NutrifitColors,
        typography = NutrifitTypography,
        content = content
    )
}