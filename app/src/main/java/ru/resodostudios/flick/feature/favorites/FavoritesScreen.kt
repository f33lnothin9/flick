package ru.resodostudios.flick.feature.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.flick.feature.movie.presentation.MovieViewModel

@Composable
internal fun FavoritesRoute(
    favoriteViewModel: FavoritesViewModel = hiltViewModel(),
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val moviesState by favoriteViewModel.state.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = moviesState,
        onDeleteClick = movieViewModel::onEvent
    )
}

@Composable
internal fun FavoritesScreen(
    state: FavoritesUiState,
    onDeleteClick: (FavoriteEvent) -> Unit
) {

    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_empty))

    if (state.movies.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(300.dp)
        ) {
            items(state.movies) { movie ->
                ListItem(
                    headlineContent = { Text(text = movie.name.toString()) },
                    leadingContent = {
                        FlickAsyncImage(
                            url = movie.image.toString(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .size(56.dp)
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = { onDeleteClick(FavoriteEvent.DeleteMovie(movie)) }) {
                            Icon(
                                FlickIcons.Delete,
                                contentDescription = "Remove from Favorites"
                            )
                        }
                    },
                    supportingContent = { Text(text = movie.rating.toString()) },
                    modifier = Modifier.clickable { }
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    modifier = Modifier.size(200.dp),
                    composition = lottieComposition
                )
                Text(
                    text = "Nothing in Favorites",
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}