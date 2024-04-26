@file:OptIn(ExperimentalFoundationApi::class)

package com.guilhermegaspar.vaultmovie.presentation.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.guilhermegaspar.vaultmovie.presentation.extension.dynamictheming.DominantColorState
import com.guilhermegaspar.vaultmovie.presentation.main.viewmodel.HomeCategory
import com.guilhermegaspar.vaultmovie.presentation.main.viewmodel.MainViewModel
import com.guilhermegaspar.vaultmovie.presentation.main.viewmodel.MainViewState
import com.guilhermegaspar.vaultmovie.presentation.movie.ui.FavoriteMovieItem
import com.guilhermegaspar.vaultmovie.presentation.movie.ui.popularMovieItems

@Composable
fun DominantColorComponent(
    viewState: MainViewState,
    pagerState: PagerState,
    dominantColorState: DominantColorState
) {
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
}

@Composable
fun TopBar(
    scrimColor: Color,
    appBarColor: Color
) {
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
}

@Composable
fun HomeContent(
    viewState: MainViewState,
    pagerState: PagerState,
    scrimColor: Color,
    viewModel: MainViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            FavoriteMovieItem(
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
                popularMovieItems(
                    items = viewState.followedMovies,
                    viewModel::onTogglePodcastFollowed
                )
            }

            HomeCategory.Discover -> {

            }
        }
    }
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