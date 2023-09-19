package ru.resodostudios.flick.feature.movie.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.domain.GetMovieExtendedUseCase
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.MovieExtended
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.flick.feature.movie.navigation.MovieArgs
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    getMovieExtendedUseCase: GetMovieExtendedUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieArgs: MovieArgs = MovieArgs(savedStateHandle)

    private val movieId = movieArgs.movieId

    val movieUiState: StateFlow<MovieUiState> = getMovieExtendedUseCase.invoke(movieId)
        .map<MovieExtended, MovieUiState>(MovieUiState::Success)
        .onStart { emit(MovieUiState.Loading) }
        .catch { emit(MovieUiState.Error(it.localizedMessage?.toString() ?: "")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieUiState.Loading,
        )

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddMovie -> {
                val favoriteMovie = FavoriteMovie(
                    id = event.movie.id,
                    image = event.movie.image.medium,
                    rating = event.movie.rating.average,
                    name = event.movie.name,
                    genres = event.movie.genres
                )

                viewModelScope.launch {
                    favoritesRepository.upsertMovie(favoriteMovie)
                }
            }

            is FavoriteEvent.DeleteMovie -> {
                viewModelScope.launch {
                    favoritesRepository.deleteMovie(event.movie)
                }
            }
        }
    }
}

sealed interface MovieUiState {

    data object Loading : MovieUiState

    data class Success(
        val data: MovieExtended
    ) : MovieUiState

    data class Error(
        val errorMessage: String
    ) : MovieUiState
}