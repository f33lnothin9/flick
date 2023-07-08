package ru.resodostudios.movies.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.resodostudios.movies.feature.favorites.domain.repository.FavoritesRepository
import ru.resodostudios.movies.feature.favorites.domain.util.MovieState
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _movies =
        repository.getMovies().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(MovieState())
    val state = combine(_state, _movies) { state, movies ->
        state.copy(
            movies = movies
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MovieState())
}