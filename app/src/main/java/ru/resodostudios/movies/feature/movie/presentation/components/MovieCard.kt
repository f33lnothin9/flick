package ru.resodostudios.movies.feature.movie.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import ru.resodostudios.movies.core.presentation.theme.Typography
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry

@ExperimentalMaterial3Api
@Composable
fun MovieCard(movie: MovieEntry, onNavigate: () -> Unit, onDelete: () -> Unit) {

    Card(onClick = onNavigate) {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.image?.medium)
                            .crossfade(400)
                            .size(Size.ORIGINAL)
                            .transformations()
                            .build(),
                        contentDescription = "Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .size(height = 118.dp, width = 84.dp),
                        filterQuality = FilterQuality.Low
                    )

                    Surface(
                        modifier = Modifier
                            .padding(start = 4.dp, top = 4.dp)
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(12.dp)),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = movie.rating?.average.toString(),
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    top = 4.dp,
                                    end = 8.dp,
                                    bottom = 4.dp
                                ),
                            style = Typography.labelLarge,
                            maxLines = 1
                        )
                    }
                }

                Column(
                    modifier = Modifier.height(118.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = movie.name.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Typography.titleLarge,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 4.dp)
                    )

                    Row {
                        movie.genres?.take(2)?.forEach { genres ->
                            Text(
                                text = "$genres ",
                                style = Typography.titleSmall
                            )
                        }
                    }

                    Text(
                        text = movie.language.toString(),
                        style = Typography.titleSmall
                    )
                }
            }

            FilledTonalIconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
            ) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
            }
        }
    }
}