package ru.resodostudios.flick.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    favoritesRepository: FavoritesRepository
) : ViewModel() {

    val favoritesUiState: SharedFlow<FavoritesUiState> =
        favoritesRepository.getMovies()
            .map<List<FavoriteMovie>, FavoritesUiState>(FavoritesUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FavoritesUiState.Loading,
            )
}

sealed interface FavoritesUiState {

    data object Loading : FavoritesUiState

    data class Success(
        val movies: List<FavoriteMovie>
    ) : FavoritesUiState
}