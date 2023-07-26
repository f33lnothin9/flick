package ru.resodostudios.flick.feature.people.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.resodostudios.flick.feature.people.domain.model.Person
import ru.resodostudios.flick.feature.people.domain.use_case.GetPeopleUseCase
import ru.resodostudios.flick.feature.people.domain.util.PeopleEvent
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople
import ru.resodostudios.flick.feature.search.domain.use_case.SearchPeopleUseCase
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val getPeopleUseCase: GetPeopleUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase
) : ViewModel() {

    private val _people = MutableStateFlow(emptyList<Person>())
    private val _searchedPeople = MutableStateFlow(emptyList<SearchedPeople>())
    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _state = MutableStateFlow(PeopleUiState())

    val state = combine(
        _state,
        _people,
        _searchedPeople,
        _isLoading,
        _isError
    ) { state, people, searchedPeople, isLoading, isError ->
        state.copy(
            people = people,
            searchedPeople = searchedPeople,
            isLoading = isLoading,
            isError = isError
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PeopleUiState())

    init {
        getPeople()
    }

    fun onEvent(event: PeopleEvent) {
        when (event) {
            is PeopleEvent.Search -> {
                searchPeople(event.query)
            }
        }
    }

    fun getPeople() {
        viewModelScope.launch {
            _isLoading.value = true
            getPeopleUseCase.invoke().let { response ->
                if (response.isSuccessful) {
                    _people.value = response.body()!!
                    _isLoading.value = false
                    _isError.value = false
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }
        }
    }

    private fun searchPeople(query: String) {
        viewModelScope.launch {
            searchPeopleUseCase.invoke(query).let {
                if (it.isSuccessful) {
                    _searchedPeople.value = it.body()!!
                    _isLoading.value = false
                    _isError.value = false
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }
        }
    }
}