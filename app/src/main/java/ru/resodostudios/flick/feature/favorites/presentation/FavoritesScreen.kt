package ru.resodostudios.flick.feature.favorites.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch
import ru.resodostudios.flick.R
import ru.resodostudios.flick.navigation.Screens
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent

@ExperimentalMaterial3Api
@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    navController: NavController,
    drawerState: DrawerState,
    onEvent: (FavoriteEvent) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_empty))
    val scope = rememberCoroutineScope()

    Box {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = { Text(text = "Favorites") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            ) {
                if (state.movies.isNotEmpty()) {
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
                                IconButton(onClick = { onEvent(FavoriteEvent.DeleteMovie(movie)) }) {
                                    Icon(
                                        Icons.Filled.Favorite,
                                        contentDescription = "Remove from Favorites"
                                    )
                                }
                            },
                            supportingContent = { Text(text = movie.rating.toString()) },
                            modifier = Modifier.clickable { navController.navigate(Screens.Movie.route + "/${movie.id}") }
                        )
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
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
            }
        }
    }
}