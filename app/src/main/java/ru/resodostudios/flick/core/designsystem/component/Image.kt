package ru.resodostudios.flick.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun FlickAsyncImage(
    url: String,
    contentDescription: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(500)
            .size(Size.ORIGINAL)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun FlickSubcomposeAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(500)
            .size(Size.ORIGINAL)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        filterQuality = FilterQuality.Low,
        contentScale = ContentScale.FillWidth,
        loading = { AnimatedShimmer() }
    )
}