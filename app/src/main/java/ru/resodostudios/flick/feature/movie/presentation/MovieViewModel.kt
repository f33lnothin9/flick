package ru.resodostudios.flick.feature.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie
import ru.resodostudios.flick.feature.favorites.domain.repository.FavoritesRepository
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.flick.feature.movie.data.model.Cast
import ru.resodostudios.flick.feature.movie.data.model.Movie
import ru.resodostudios.flick.feature.movie.domain.use_case.GetCastUseCase
import ru.resodostudios.flick.feature.movie.domain.use_case.GetMovieUseCase
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: GetMovieUseCase,
    private val getCastUseCase: GetCastUseCase,
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _movie = MutableStateFlow(Movie())
    private val _cast = MutableStateFlow(emptyList<Cast>())
    private val _isLoading = MutableStateFlow(true)
    private val _isError = MutableStateFlow(false)
    private val _state = MutableStateFlow(MovieUiState())

    val state = combine(
        _state,
        _movie,
        _cast,
        _isLoading,
        _isError
    ) { state, movie, cast, isLoading, isError ->
        state.copy(
            movie = movie,
            cast = cast,
            isLoading = isLoading,
            isError = isError
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MovieUiState())

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

    fun getCast(id: Int) {
        viewModelScope.launch {
            getCastUseCase.invoke(id).let {
                if (it.isSuccessful) {
                    _cast.value = it.body()!!
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