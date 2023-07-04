package ru.resodostudios.movies.feature.movies.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import ru.resodostudios.movies.core.presentation.navigation.Screens
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry

@ExperimentalMaterial3Api
@Composable
fun MovieCard(movie: MovieEntry, navController: NavController) {

    Card(
        onClick = { navController.navigate(Screens.Movie.route + "/${movie.id}") },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            movie.image?.let {
                it.original?.let { url ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .crossfade(400)
                            .size(Size.ORIGINAL)
                            .transformations()
                            .build(),
                        contentDescription = "Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }
    }
}