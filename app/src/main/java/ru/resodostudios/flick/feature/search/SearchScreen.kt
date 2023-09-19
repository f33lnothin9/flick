package ru.resodostudios.flick.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.SearchBar
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.core.model.data.SearchMovie
import ru.resodostudios.flick.core.model.data.SearchPerson
import ru.resodostudios.flick.core.ui.EmptyState
import ru.resodostudios.flick.core.ui.LoadingState

@Composable
internal fun SearchRoute(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchState by searchViewModel.searchUiState.collectAsStateWithLifecycle()

    SearchScreen(
        onBackClick = onBackClick,
        onSearch = searchViewModel::onSearchQueryChanged,
        searchState = searchState,
        onMovieClick = onMovieClick,
        onPersonClick = onPersonClick
    )
}

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onSearch: (String) -> Unit,
    searchState: SearchUiState
) {
    SearchBar(
        titleRes = R.string.search,
        onSearch = onSearch,
        onBackClick = onBackClick
    ) {
        when (searchState) {
            SearchUiState.Loading -> LoadingState()
            is SearchUiState.Error -> EmptyState(
                message = searchState.errorMessage,
                animationId = R.raw.anim_error_1
            )

            is SearchUiState.Success -> LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(300.dp)
            ) {
                if (searchState.data.movies.isNotEmpty()) {
                    searchMovies(
                        searchMovies = searchState.data.movies,
                        onMovieClick = onMovieClick
                    )
                }
                if (searchState.data.people.isNotEmpty()) {
                    searchPeople(
                        searchPeople = searchState.data.people,
                        onPersonClick = onPersonClick
                    )
                }
            }
        }
    }
}

private fun LazyGridScope.searchMovies(
    searchMovies: List<SearchMovie>,
    onMovieClick: (Int) -> Unit
) {
    item {
        Text(
            text = stringResource(R.string.movies),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 32.dp, top = 16.dp, bottom = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
    items(searchMovies) { searchMovie ->
        ListItem(
            headlineContent = { Text(text = searchMovie.movie.name) },
            supportingContent = {
                Text(
                    text = "${searchMovie.movie.rating.average} • ${
                        searchMovie.movie.genres.take(2).joinToString(", ")
                    }"
                )
            },
            leadingContent = { Icon(FlickIcons.Search, contentDescription = null) },
            modifier = Modifier
                .clickable { onMovieClick(searchMovie.movie.id) }
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

private fun LazyGridScope.searchPeople(
    searchPeople: List<SearchPerson>,
    onPersonClick: (Int) -> Unit
) {
    item {
        Text(
            text = stringResource(R.string.people),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 32.dp, top = 16.dp, bottom = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
    items(searchPeople) { searchPerson ->
        ListItem(
            headlineContent = { Text(text = searchPerson.person.name) },
            supportingContent = { Text(text = searchPerson.person.country.name) },
            leadingContent = { Icon(FlickIcons.Search, contentDescription = null) },
            modifier = Modifier
                .clickable { onPersonClick(searchPerson.person.id) }
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}