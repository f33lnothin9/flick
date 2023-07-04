package ru.resodostudios.movies.feature.movies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import ru.resodostudios.movies.core.presentation.navigation.Screens
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry
import ru.resodostudios.movies.feature.movies.presentation.MoviesViewModel

@ExperimentalMaterial3Api
@Composable
fun SearchBar(
    viewModel: MoviesViewModel,
    movies: List<MovieEntry>,
    navController: NavController,
    onMenuClick: () -> Unit
) {

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .semantics { isContainer = true }
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = text,
            onQueryChange = {
                text = it
                viewModel.searchMovie(it)
            },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Search movies") },
            leadingIcon = {
                if (active) {
                    IconButton(onClick = { active = false }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                } else {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            },
            trailingIcon = {
                if (text.isNotBlank()) {
                    IconButton(
                        onClick = {
                            text = ""
                            viewModel.searchMovie("")
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            windowInsets = WindowInsets.statusBars
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (text.isNotBlank()) {
                    items(movies) { movie ->
                        ListItem(
                            headlineContent = { movie.name?.let { Text(it) } },
                            supportingContent = {
                                Row {
                                    movie.genres?.take(2)?.forEach {
                                        Text(text = "$it ")
                                    }
                                }
                            },
                            leadingContent = { Icon(Icons.Filled.Search, contentDescription = null) },
                            modifier = Modifier.clickable {
                                navController.navigate(Screens.Movie.route + "/${movie.id}")
                                active = false
                            }
                        )
                    }
                }
            }
        }
    }
}