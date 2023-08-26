package ru.resodostudios.flick.feature.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.SearchBar

@Composable
internal fun SearchRoute(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

}

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit,
    onSearch: (String) -> Unit
) {
    SearchBar(
        titleRes = R.string.search,
        onSearch = onSearch,
        onBackClick = onBackClick
    ) {

    }
}