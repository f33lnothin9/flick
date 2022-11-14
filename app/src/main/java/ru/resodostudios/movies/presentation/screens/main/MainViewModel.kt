package ru.resodostudios.movies.presentation.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.resodostudios.movies.data.models.Movie
import ru.resodostudios.movies.data.network.ApiRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository): ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    private val _isLoading = MutableStateFlow(true)

    val movies: LiveData<List<Movie>>
        get() = _movies
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMovies().let {
                if (it.isSuccessful) {
                    _movies.postValue(it.body())
                    _isLoading.value = false
                } else {
                    Log.d("data", "Error")
                }
            }
        }
    }
}