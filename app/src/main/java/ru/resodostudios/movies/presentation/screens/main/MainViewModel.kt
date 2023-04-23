package ru.resodostudios.movies.presentation.screens.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.resodostudios.movies.data.models.Movie
import ru.resodostudios.movies.data.network.ApiRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository): ViewModel() {

    private val _movies = MutableStateFlow(emptyList<Movie>())
    private val _isLoading = MutableStateFlow(true)

    private var _cachedMovies = listOf<Movie>()
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
                it.name.contains(query.trim(), ignoreCase = true)
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