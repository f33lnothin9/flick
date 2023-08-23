package ru.resodostudios.flick.feature.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.flick.feature.movie.domain.use_case.GetCastUseCase
import ru.resodostudios.flick.feature.movie.domain.use_case.GetCrewUseCase
import ru.resodostudios.flick.feature.movie.domain.use_case.GetMovieUseCase
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: GetMovieUseCase,
    private val getCastUseCase: GetCastUseCase,
    private val getCrewUseCase: GetCrewUseCase,
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _movie = MutableStateFlow(Movie())
    private val _cast = MutableStateFlow(emptyList<Cast>())
    private val _crew = MutableStateFlow(emptyList<Crew>())
    private val _state = MutableStateFlow(MovieUiState())

    val state = combine(
        _state,
        _movie,
        _cast,
        _crew
    ) { state, movie, cast, crew ->
        state.copy(
            movie = movie,
            cast = cast,
            crew = crew
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MovieUiState())

    fun getMovie(id: Int) {
        viewModelScope.launch {
            movieUseCase.invoke(id).let {
                if (it.isSuccessful) {
                    _movie.value = it.body()!!
                    _state.update { state ->
                        state.copy(
                            isError = false
                        )
                    }
                } else {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun getCast(id: Int) {
        viewModelScope.launch {
            getCastUseCase.invoke(id).let {
                if (it.isSuccessful) {
                    _cast.value = it.body()!!
                    _state.update { state ->
                        state.copy(
                            isError = false
                        )
                    }
                } else {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun getCrew(id: Int) {
        viewModelScope.launch {
            getCrewUseCase.invoke(id).let {
                if (it.isSuccessful) {
                    _crew.value = it.body()!!
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false
                        )
                    }
                } else {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddMovie -> {
                val favoriteMovie = FavoriteMovieEntity(
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