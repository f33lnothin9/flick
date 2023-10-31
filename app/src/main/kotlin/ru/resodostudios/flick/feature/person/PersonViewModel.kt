package ru.resodostudios.flick.feature.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.domain.GetPersonExtendedUseCase
import ru.resodostudios.flick.core.model.data.FavoritePerson
import ru.resodostudios.flick.core.model.data.PersonExtended
import ru.resodostudios.flick.feature.favorites.FavoriteEvent
import ru.resodostudios.flick.feature.person.navigation.PersonArgs
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    getPersonExtendedUseCase: GetPersonExtendedUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val personArgs: PersonArgs = PersonArgs(savedStateHandle)

    private val personId = personArgs.personId

    val personUiState: StateFlow<PersonUiState> = getPersonExtendedUseCase.invoke(personId)
        .map<PersonExtended, PersonUiState>(PersonUiState::Success)
        .onStart { emit(PersonUiState.Loading) }
        .catch { emit(PersonUiState.Error(it.localizedMessage?.toString() ?: "")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PersonUiState.Loading,
        )

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddPerson -> {
                val favoritePerson = FavoritePerson(
                    id = event.person.id,
                    image = event.person.image.medium,
                    name = event.person.name
                )

                viewModelScope.launch {
                    favoritesRepository.upsertPerson(favoritePerson)
                }
            }

            is FavoriteEvent.DeletePerson -> {
                viewModelScope.launch {
                    favoritesRepository.deletePerson(event.person)
                }
            }

            else -> {}
        }
    }
}

sealed interface PersonUiState {

    data object Loading : PersonUiState

    data class Success(
        val data: PersonExtended
    ) : PersonUiState

    data class Error(
        val errorMessage: String
    ) : PersonUiState
}