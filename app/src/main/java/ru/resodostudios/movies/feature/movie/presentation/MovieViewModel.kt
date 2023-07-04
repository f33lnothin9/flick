package ru.resodostudios.movies.feature.movie.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.resodostudios.movies.feature.movie.data.model.Movie
import ru.resodostudios.movies.feature.movie.domain.use_case.GetMovieUseCase
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow(Movie())
    private val _isLoading = MutableStateFlow(true)

    val movie = _movie.asStateFlow()
    val isLoading = _isLoading.asStateFlow()

    fun getMovie(id: String) {
        viewModelScope.launch {
            movieUseCase.invoke(id).let {
                if (it.isSuccessful) {
                    _movie.value = it.body()!!
                    _isLoading.value = false
                } else {
                    Log.d("data", "Error")
                }
            }
        }
    }
}