package ru.resodostudios.flick.feature.movies.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.resodostudios.flick.core.presentation.components.RetrySection
import ru.resodostudios.flick.core.presentation.navigation.Screens
import ru.resodostudios.flick.feature.movies.domain.util.MoviesEvent
import ru.resodostudios.flick.feature.movies.presentation.components.MovieCard
import ru.resodostudios.flick.feature.search.presentation.components.SearchBar

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MoviesScreen(
    navController: NavController,
    state: MoviesUiState,
    onEvent: (MoviesEvent) -> Unit,
    onRetry: () -> Unit,
    drawerState: DrawerState
) {

    var selected by remember { mutableStateOf(false) }
    var filtersOpened by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchBar(
                onSearch = { onEvent(MoviesEvent.Search(it)) },
                onMenuClick = { scope.launch { drawerState.open() } },
                onClearSearch = { onEvent(MoviesEvent.Search(it)) },
                onFilterClick = { filtersOpened = !filtersOpened },
                title = "Search movies",
                content = {
                    items(state.searchedMovies) { searchedMovie ->
                        ListItem(
                            headlineContent = { searchedMovie.movie?.name?.let { Text(it) } },
                            supportingContent = {
                                Text(
                                    text = searchedMovie.movie?.genres?.take(2)?.joinToString(", ")
                                        .toString(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.clickable { navController.navigate(Screens.Movie.route + "/${searchedMovie.movie?.id}") }
                        )
                    }
                }
            )

            if (filtersOpened) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selected,
                            onClick = { selected = !selected },
                            label = { Text("Genre") },
                            leadingIcon = {
                                if (selected) {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "Localized Description",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            },
                            trailingIcon = {
                                if (!selected) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = "Localized Description",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            }
                        )
                    }

                    item {
                        FilterChip(
                            selected = selected,
                            onClick = { selected = !selected },
                            label = { Text("Language") },
                            leadingIcon = {
                                if (selected) {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "Localized Description",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            },
                            trailingIcon = {
                                if (!selected) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = "Localized Description",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn()
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .statusBarsPadding()
                    .then(
                        if (filtersOpened) {
                            Modifier.padding(start = 16.dp, end = 16.dp, top = 128.dp)
                        } else {
                            Modifier.padding(start = 16.dp, end = 16.dp, top = 80.dp)
                        }
                    ),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                columns = StaggeredGridCells.Adaptive(150.dp)
            ) {
                items(state.movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onNavigate = { navController.navigate(Screens.Movie.route + "/${movie.id}") }
                    )
                }
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (state.isError) RetrySection(onClick = onRetry)
    }
}