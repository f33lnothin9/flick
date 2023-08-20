package ru.resodostudios.flick.feature.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons

@Composable
internal fun FavoritesRoute(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val moviesState by viewModel.state.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = moviesState
    )
}

@Composable
internal fun FavoritesScreen(
    state: FavoritesUiState
) {

    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_empty))

    if (state.movies.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.movies) { movie ->
                ListItem(
                    headlineContent = { Text(text = movie.name.toString()) },
                    leadingContent = {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(movie.image)
                                .crossfade(400)
                                .size(Size.ORIGINAL)
                                .transformations()
                                .build(),
                            contentDescription = "Image",
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .size(56.dp),
                            filterQuality = FilterQuality.Low,
                            contentScale = ContentScale.Crop
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = {  }) {
                            Icon(
                                FlickIcons.Delete,
                                contentDescription = "Remove from Favorites"
                            )
                        }
                    },
                    supportingContent = { Text(text = movie.rating.toString()) },
                    modifier = Modifier.clickable {  }
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    modifier = Modifier.size(256.dp),
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