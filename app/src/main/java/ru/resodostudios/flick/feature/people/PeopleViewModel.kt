package ru.resodostudios.flick.feature.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.feature.people.PeopleUiState.Error
import ru.resodostudios.flick.feature.people.PeopleUiState.Loading
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
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