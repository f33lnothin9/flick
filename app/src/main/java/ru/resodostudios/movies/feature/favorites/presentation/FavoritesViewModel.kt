package ru.resodostudios.movies.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.movies.feature.favorites.data.data_source.MovieDao
import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie
import ru.resodostudios.movies.feature.favorites.domain.util.MovieEvent
import ru.resodostudios.movies.feature.favorites.domain.util.MovieState
import ru.resodostudios.movies.feature.movie.data.model.Movie
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dao: MovieDao
) : ViewModel() {

    private val _movies =
        dao.getMovies().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(MovieState())
    val state = combine(_state, _movies) { state, movies ->
        state.copy(
            movies = movies
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MovieState())

    fun onEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.AddMovie -> {
                val eventMovie = event.movie
                val favoriteMovie = FavoriteMovie(
                    id = eventMovie.id,
                    image = eventMovie.image?.original,
                    rating = eventMovie.rating?.average,
                    name = eventMovie.name
                )

                viewModelScope.launch {
                    dao.upsertMovie(favoriteMovie)
                }
            }

            is MovieEvent.DeleteMovie -> {
                viewModelScope.launch {
                    dao.deleteMovie(event.movie)
                }
            }
        }
    }
}