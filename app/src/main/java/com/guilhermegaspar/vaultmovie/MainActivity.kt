@file:OptIn(ExperimentalFoundationApi::class)

package com.guilhermegaspar.vaultmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guilhermegaspar.vaultmovie.recipes.FollowedPodcastItem
import com.guilhermegaspar.vaultmovie.recipes.recipesItems
import com.guilhermegaspar.vaultmovie.ui.theme.MinContrastOfPrimaryVsSurface
import com.guilhermegaspar.vaultmovie.ui.theme.NutrifitTheme
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
            NutrifitTheme {
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

                            val selectedImageUrl =
                                viewState.followedMovies.getOrNull(pagerState.currentPage)
                                    ?.imageUrl

                            // When the selected image url changes, call updateColorsFromImageUrl() or reset()
                            LaunchedEffect(selectedImageUrl) {
                                if (selectedImageUrl != null) {
                                    dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
                                } else {
                                    dominantColorState.reset()
                                }
                            }

                            val scrimColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)

                            // Top Bar
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = scrimColor)
                            ) {
                                // Draw a scrim over the status bar which matches the app bar
                                Spacer(
                                    Modifier
                                        .background(appBarColor)
                                        .fillMaxWidth()
                                        .windowInsetsTopHeight(WindowInsets.statusBars)
                                )
                                HomeAppBar(
                                    backgroundColor = appBarColor,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            LazyColumn(modifier = Modifier.fillMaxSize()) {

                                item {
                                    FollowedPodcastItem(
                                        items = viewState.followedMovies,
                                        pagerState = pagerState,
                                        onPodcastUnfollowed = {},
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent, // Cor transparente no topo
                                                        scrimColor // Cor da sobreposição na parte inferior
                                                    ),
                                                )
                                            )
                                    )
                                }

                                stickyHeader {
                                    HomeTabs(
                                        listOf(HomeCategory.Discover, HomeCategory.Library),
                                        viewState.selectedHomeCategory,
                                        {
                                            viewModel.onHomeCategorySelected(it)
                                        }
                                    )
                                }

                                when (viewState.selectedHomeCategory) {
                                    HomeCategory.Library -> {
                                        recipesItems(
                                            items = viewState.followedMovies,
                                            viewModel::onTogglePodcastFollowed
                                        )
                                    }

                                    HomeCategory.Discover -> {

                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NutrifitTheme {
        Greeting("Android")
    }
}

@Composable
fun GradientCard() {
    Card(
        modifier = Modifier
            .background(
                color = Color.Black,
            )
            .alpha(0.5f)
    ) {
        Greeting("Android")
    }
}

@Composable
private fun HomeTabs(
    categories: List<HomeCategory>,
    selectedCategory: HomeCategory,
    onCategorySelected: (HomeCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        HomeTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }

    TabRow(
        selectedTabIndex = selectedIndex,
        indicator = indicator,
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) },
                text = {
                    Text(
                        text = when (category) {
                            HomeCategory.Library -> "Flores"
                            HomeCategory.Discover -> "Plantas"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            )
        }
    }
}

enum class HomeCategory {
    Library, Discover
}

@Composable
fun HomeTabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}