package ru.resodostudios.movies.feature.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry
import ru.resodostudios.movies.feature.movies.domain.use_case.GetMoviesUseCase
import ru.resodostudios.movies.feature.movies.domain.util.MoviesEvent
import ru.resodostudios.movies.feature.search.data.model.SearchedMovie
import ru.resodostudios.movies.feature.search.domain.use_case.SearchMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase,
    private val searchUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow(emptyList<MovieEntry>())
    private val _searchedMovies = MutableStateFlow(emptyList<SearchedMovie>())
    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _state = MutableStateFlow(MoviesUiState())

    val state = combine(
        _state,
        _movies,
        _isLoading,
        _isError,
        _searchedMovies
    ) { state, movies, isLoading, isError, searchedMovies ->
        state.copy(
            movies = movies,
            searchedMovies = searchedMovies,
            isLoading = isLoading,
            isError = isError
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MoviesUiState())

    init {
        getMovies()
    }

    fun onEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.Search -> {
                searchMovies(event.query)
            }
        }
    }

    fun getMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            moviesUseCase.invoke().let { response ->
                if (response.isSuccessful) {
                    _movies.value = response.body()!!
                    _isLoading.value = false
                    _isError.value = false
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            searchUseCase.invoke(query).let {
                if (it.isSuccessful) {
                    _searchedMovies.value = it.body()!!
                    _isLoading.value = false
                    _isError.value = false
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }
        }
    }
}