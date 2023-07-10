package ru.resodostudios.movies.feature.movies.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry
import ru.resodostudios.movies.feature.movies.domain.use_case.GetMoviesUseCase
import ru.resodostudios.movies.feature.movies.domain.util.MoviesEvent
import ru.resodostudios.movies.feature.movies.domain.util.MoviesState
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow(emptyList<MovieEntry>())
    private val _isLoading = MutableStateFlow(true)
    private val _isError = MutableStateFlow(false)
    private val _state = MutableStateFlow(MoviesState())

    private var _cachedMovies = listOf<MovieEntry>()
    private var _isSearching = mutableStateOf(false)
    private var _isSearchStarting = true

    val state = combine(_state, _movies, _isLoading, _isError) { state, movies, isLoading, isError ->
        state.copy(
            movies = movies,
            isLoading = isLoading,
            isError = isError
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MoviesState())

    init {
        getMovies()
    }

    fun onEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.Search -> {
                val listToSearch = if (_isSearchStarting) {
                    _movies.value
                } else {
                    _cachedMovies
                }
                viewModelScope.launch(Dispatchers.Default) {
                    if (event.query.isEmpty()) {
                        _movies.value = _cachedMovies
                        _isSearching.value = false
                        _isSearchStarting = true
                        return@launch
                    }
                    val results = listToSearch.filter {
                        it.name?.contains(event.query.trim(), ignoreCase = true) ?: false
                    }
                    if (_isSearchStarting) {
                        _cachedMovies = _movies.value
                        _isSearchStarting = false
                    }
                    _movies.value = results
                    _isSearching.value = true
                }
            }
        }
    }

    fun getMovies() {
        viewModelScope.launch {
            moviesUseCase.invoke().let {
                if (it.isSuccessful) {
                    _movies.value = it.body()!!
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