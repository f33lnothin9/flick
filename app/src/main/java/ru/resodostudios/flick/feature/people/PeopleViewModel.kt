package ru.resodostudios.flick.feature.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.model.data.Person
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    val peopleUiState: StateFlow<PeopleUiState> =
        peopleRepository.getPeople()
            .map<List<Person>, PeopleUiState>(PeopleUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PeopleUiState.Loading,
            )
}

sealed interface PeopleUiState {

    data object Loading : PeopleUiState

    data class Success(
        val people: List<Person>
    ) : PeopleUiState

    data object Error : PeopleUiState
}