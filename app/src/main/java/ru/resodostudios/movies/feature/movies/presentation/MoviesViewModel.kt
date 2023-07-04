package ru.resodostudios.movies.feature.movies.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry
import ru.resodostudios.movies.feature.movies.data.repository.MoviesApiRepository
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesApiRepository
) : ViewModel() {

    private val _movies = MutableStateFlow(emptyList<MovieEntry>())
    private val _isLoading = MutableStateFlow(true)

    private var _cachedMovies = listOf<MovieEntry>()
    private var _isSearching = mutableStateOf(false)
    private var _isSearchStarting = true

    val movies = _movies.asStateFlow()
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMovies().let {
                if (it.isSuccessful) {
                    _movies.value = it.body()!!
                    _isLoading.value = false
                } else {
                    Log.d("data", "Error")
                }
            }
        }
    }

    fun searchMovie(query: String) {
        val listToSearch = if (_isSearchStarting) {
            _movies.value
        } else {
            _cachedMovies
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                _movies.value = _cachedMovies
                _isSearching.value = false
                _isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name?.contains(query.trim(), ignoreCase = true) ?: false
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