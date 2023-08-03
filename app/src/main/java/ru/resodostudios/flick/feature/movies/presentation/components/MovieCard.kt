package ru.resodostudios.flick.feature.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.resodostudios.flick.core.presentation.components.AnimatedShimmer
import ru.resodostudios.flick.core.presentation.theme.Typography
import ru.resodostudios.flick.feature.movies.data.model.MovieEntry

@ExperimentalMaterial3Api
@Composable
fun MovieCard(movie: MovieEntry, onNavigate: () -> Unit) {

    Card(onClick = onNavigate) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.image?.original)
                        .crossfade(400)
                        .size(175)
                        .build(),
                    contentDescription = "Image",
                    modifier = Modifier
                        .defaultMinSize(minWidth = 200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    filterQuality = FilterQuality.Low,
                    contentScale = ContentScale.FillWidth,
                    loading = { AnimatedShimmer() }
                )

                Surface(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(12.dp)),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = (movie.rating?.average ?: 0.0).toString(),
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 2.dp,
                                end = 8.dp,
                                bottom = 2.dp
                            ),
                        style = Typography.labelLarge,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = movie.name.toString(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )

                Text(
                    text = movie.genres?.take(2)?.joinToString(", ") ?: "Empty",
                    style = Typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}