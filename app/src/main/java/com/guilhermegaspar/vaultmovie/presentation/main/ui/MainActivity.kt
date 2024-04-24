@file:OptIn(ExperimentalFoundationApi::class)

package com.guilhermegaspar.vaultmovie.presentation.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.guilhermegaspar.vaultmovie.presentation.extension.dynamictheming.DynamicThemePrimaryColorsFromImage
import com.guilhermegaspar.vaultmovie.presentation.extension.color.contrastAgainst
import com.guilhermegaspar.vaultmovie.presentation.home.ui.DominantColorComponent
import com.guilhermegaspar.vaultmovie.presentation.home.ui.HomeContent
import com.guilhermegaspar.vaultmovie.presentation.home.ui.TopBar
import com.guilhermegaspar.vaultmovie.presentation.main.viewmodel.MainViewModel
import com.guilhermegaspar.vaultmovie.presentation.extension.dynamictheming.rememberDominantColorState
import com.guilhermegaspar.vaultmovie.presentation.core.theme.MinContrastOfPrimaryVsSurface
import com.guilhermegaspar.vaultmovie.presentation.core.theme.VaultMovieTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModel()

        enableEdgeToEdge(
            // This app is only ever in dark mode, so hard code detectDarkMode to true.
            SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
                detectDarkMode = { true })
        )

        setContent {
            VaultMovieTheme {
                // A surface container using the 'background' color from the theme
                val viewState by viewModel.state.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                        )
                    ) {


                        val surfaceColor = MaterialTheme.colorScheme.surface
                        val appBarColor = surfaceColor.copy(alpha = 0.87f)
                        val dominantColorState = rememberDominantColorState { color ->
                            // We want a color which has sufficient contrast against the surface color
                            color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
                        }

                        DynamicThemePrimaryColorsFromImage(dominantColorState) {
                            val pagerState = rememberPagerState { viewState.followedMovies.size }
                            DominantColorComponent(viewState, pagerState, dominantColorState)
                            val scrimColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)
                            TopBar(scrimColor, appBarColor)
                            HomeContent(viewState, pagerState, scrimColor, viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VaultMovieTheme {

    }
}