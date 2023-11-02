package ru.resodostudios.flick.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.domain.GetFavoritesUseCase
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.FavoritePerson
import ru.resodostudios.flick.core.model.data.Favorites
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.model.data.Person
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
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

    fun addMovie(movie: Movie) {
        val favoriteMovie = FavoriteMovie(
            id = movie.id,
            image = movie.image.medium,
            rating = movie.rating.average,
            name = movie.name,
            genres = movie.genres
        )

        viewModelScope.launch {
            favoritesRepository.upsertMovie(favoriteMovie)
        }
    }

    fun removeMovie(movie: Movie) {
        val favoriteMovie = FavoriteMovie(
            id = movie.id,
            image = movie.image.medium,
            rating = movie.rating.average,
            name = movie.name,
            genres = movie.genres
        )

        viewModelScope.launch {
            favoritesRepository.deleteMovie(favoriteMovie)
        }
    }

    fun addPerson(person: Person) {
        val favoritePerson = FavoritePerson(
            id = person.id,
            image = person.image.medium,
            name = person.name
        )

        viewModelScope.launch {
            favoritesRepository.upsertPerson(favoritePerson)
        }
    }

    fun removePerson(person: Person) {
        val favoritePerson = FavoritePerson(
            id = person.id,
            image = person.image.medium,
            name = person.name
        )

        viewModelScope.launch {
            favoritesRepository.deletePerson(favoritePerson)
        }
    }
}

sealed interface FavoritesUiState {

    data object Loading : FavoritesUiState

    data class Success(
        val data: Favorites
    ) : FavoritesUiState
}