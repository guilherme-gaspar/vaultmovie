package com.guilhermegaspar.vaultmovie.presentation.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun VaultMovieTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = VaultMovieColors,
        typography = VaultMovieTypography,
        content = content
    )
}