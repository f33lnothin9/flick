package ru.resodostudios.movies.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun CoilImage(url: String, width: Dp, height: Dp) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(400)
            .size(Size.ORIGINAL)
            .build(),
        contentDescription = "Movie Cover",
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .size(height = height, width = width)
    )
}