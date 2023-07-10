package ru.resodostudios.movies.feature.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie
import ru.resodostudios.movies.feature.favorites.domain.repository.FavoritesRepository
import ru.resodostudios.movies.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.movies.feature.movie.data.model.Movie
import ru.resodostudios.movies.feature.movie.domain.use_case.GetMovieUseCase
import ru.resodostudios.movies.feature.movie.domain.util.MovieState
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: GetMovieUseCase,
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _movie = MutableStateFlow(Movie())
    private val _isLoading = MutableStateFlow(true)
    private val _isError = MutableStateFlow(false)
    private val _state = MutableStateFlow(MovieState())

    val state = combine(_state, _movie, _isLoading, _isError) { state, movie, isLoading, isError ->
        state.copy(
            movie = movie,
            isLoading = isLoading,
            isError = isError
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MovieState())

    fun getMovie(id: Int) {
        viewModelScope.launch {
            movieUseCase.invoke(id).let {
                if (it.isSuccessful) {
                    _movie.value = it.body()!!
                    _isLoading.value = false
                    _isError.value = false
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }
        }
    }

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddMovie -> {
                val favoriteMovie = FavoriteMovie(
                    id = event.movie.id,
                    image = event.movie.image?.medium,
                    rating = event.movie.rating?.average,
                    name = event.movie.name
                )

                viewModelScope.launch {
                    repository.upsertMovie(favoriteMovie)
                }
            }

            is FavoriteEvent.DeleteMovie -> {
                viewModelScope.launch {
                    repository.deleteMovie(event.movie)
                }
            }
        }
    }
}