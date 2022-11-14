package ru.resodostudios.movies.util

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun CoilImage(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(400)
            .size(Size.ORIGINAL)
            .build(),
        contentDescription = "Image",
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .size(height = 128.dp, width = 91.dp)
    )
}