package ru.resodostudios.movies.feature.favorites.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@ExperimentalMaterial3Api
@Composable
fun FavoriteCard(imageUrl: String, onNavigate: () -> Unit, onDelete: () -> Unit) {

    Surface(onClick = onNavigate, shape = RoundedCornerShape(12.dp)) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(400)
                    .size(Size.ORIGINAL)
                    .transformations()
                    .build(),
                contentDescription = "Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .size(height = 160.dp, width = 121.dp)
            )

            FilledTonalIconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Remove from Favorites")
            }
        }
    }
}