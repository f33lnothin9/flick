package ru.resodostudio.flick.feature.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.resodostudio.core.data.repository.PeopleRepository
import ru.resodostudio.flick.core.model.data.Person
import ru.resodostudio.flick.feature.people.PeopleUiState.Error
import ru.resodostudio.flick.feature.people.PeopleUiState.Loading
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    peopleRepository: PeopleRepository
) : ViewModel() {

    val peopleUiState: StateFlow<PeopleUiState> = peopleRepository.getPeople()
        .map<List<Person>, PeopleUiState>(PeopleUiState::Success)
        .onStart { emit(Loading) }
        .catch { emit(Error(it.localizedMessage?.toString() ?: "")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Loading,
        )
}

sealed interface PeopleUiState {

    data object Loading : PeopleUiState

    data class Success(
        val people: List<Person>
    ) : PeopleUiState

    data class Error(
        val errorMessage: String
    ) : PeopleUiState
}