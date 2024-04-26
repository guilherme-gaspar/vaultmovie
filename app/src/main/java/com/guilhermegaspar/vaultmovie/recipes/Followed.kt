package com.guilhermegaspar.vaultmovie.recipes

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.guilhermegaspar.vaultmovie.R
import com.guilhermegaspar.vaultmovie.domain.model.FavoriteMovie
import com.guilhermegaspar.vaultmovie.presentation.core.theme.Keyline1
import kotlinx.collections.immutable.PersistentList


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMovieItem(
    items: PersistentList<FavoriteMovie>,
    pagerState: PagerState,
    onPodcastUnfollowed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Spacer(Modifier.height(16.dp))

        PopularMovies(
            items = items,
            pagerState = pagerState,
            onPodcastUnfollowed = onPodcastUnfollowed,
            modifier = Modifier
                .padding(start = Keyline1, top = 16.dp, end = Keyline1)
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMovies(
    items: PersistentList<FavoriteMovie>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onPodcastUnfollowed: (String) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        val (id, imageUrl, title) = items[page]
        PopularMovieCarouselItem(
            podcastImageUrl = imageUrl,
            podcastTitle = title,
            onUnfollowedClick = { onPodcastUnfollowed("podcast.uri") },
            lastEpisodeDateText =  title,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        )
    }
}


@Composable
private fun PopularMovieCarouselItem(
    modifier: Modifier = Modifier,
    podcastImageUrl: String? = null,
    podcastTitle: String? = null,
    lastEpisodeDateText: String? = null,
    onUnfollowedClick: () -> Unit,
) {
    Column(
        modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .aspectRatio(1f)
        ) {
            if (podcastImageUrl != null) {
                AsyncImage(
                    model = podcastImageUrl,
                    contentDescription = podcastTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                )
            }

            ToggleFollowPopularMovieIconButton(
                onClick = onUnfollowedClick,
                isFollowed = true, /* All podcasts are followed in this feed */
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        if (lastEpisodeDateText != null) {
            CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
                Text(
                    text = lastEpisodeDateText,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun ToggleFollowPopularMovieIconButton(
    isFollowed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clickLabel = stringResource(if (isFollowed) R.string.cd_unfollow else R.string.cd_follow)
    IconButton(
        onClick = onClick,
        modifier = modifier.semantics {
            onClick(label = clickLabel, action = null)
        }
    ) {
        Icon(
            // TODO: think about animating these icons
            imageVector = when {
                isFollowed -> Icons.Default.Check
                else -> Icons.Default.Add
            },
            contentDescription = when {
                isFollowed -> stringResource(R.string.cd_add)
                else -> stringResource(R.string.cd_add)
            },
            tint = animateColorAsState(
                when {
                    isFollowed -> LocalContentColor.current
                    else -> Color.Black.copy(alpha = 1.00f)
                }
            ).value,
            modifier = Modifier
                .shadow(
                    elevation = animateDpAsState(if (isFollowed) 0.dp else 1.dp).value,
                    shape = MaterialTheme.shapes.small
                )
                .background(
                    color = animateColorAsState(
                        when {
                            isFollowed -> MaterialTheme.colorScheme.surface.copy(0.38f)
                            else -> Color.White
                        }
                    ).value,
                    shape = MaterialTheme.shapes.small
                )
                .padding(4.dp)
        )
    }
}

