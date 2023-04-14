package ru.resodostudios.movies.presentation.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.resodostudios.movies.presentation.screens.main.components.MovieCard

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val movies = viewModel.movies.observeAsState(listOf()).value

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Surface(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .semantics { isContainer = true }
                .zIndex(1f)
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
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                windowInsets = WindowInsets.statusBars
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(movies) { movie ->
                        ListItem(
                            headlineContent = { Text(movie.name) },
                            supportingContent = {
                                Row {
                                    movie.genres.take(2).forEach {
                                        Text(text = "$it ")
                                    }
                                }
                            },
                            leadingContent = { Icon(Icons.Filled.Search, contentDescription = null) },
                            modifier = Modifier.clickable {
                                text = movie.name
                                active = false
                            }
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.statusBarsPadding())
                Spacer(modifier = Modifier.requiredHeight(70.dp))
            }
            items(movies) { movie ->
                MovieCard(movie = movie, navController = navController)
            }
        }
    }
}