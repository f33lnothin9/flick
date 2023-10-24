package ru.resodostudios.flick.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.resodostudios.flick.core.domain.GetFavoritesUseCase
import ru.resodostudios.flick.core.model.data.Favorites
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val favoritesUiState: SharedFlow<FavoritesUiState> =
        getFavoritesUseCase.invoke()
            .map<Favorites, FavoritesUiState>(FavoritesUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FavoritesUiState.Loading,
            )
}

sealed interface FavoritesUiState {

    data object Loading : FavoritesUiState

    data class Success(
        val data: Favorites
    ) : FavoritesUiState
}