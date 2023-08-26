package ru.resodostudios.flick.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.resodostudios.flick.core.data.repository.SearchRepository
import ru.resodostudios.flick.core.model.data.SearchMovie
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    val searchUiState: StateFlow<SearchUiState> = searchQuery.flatMapLatest { query ->
        searchRepository.searchMovies(query)
            .map<List<SearchMovie>, SearchUiState>(SearchUiState::Success)
            .onStart { emit(SearchUiState.Loading) }
            .catch { emit(SearchUiState.Error(it.localizedMessage?.toString() ?: "")) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchUiState.Loading
    )

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    sealed interface SearchUiState {

        data object Loading : SearchUiState

        data class Success(
            val searchMovies: List<SearchMovie>
        ) : SearchUiState

        data class Error(
            val errorMessage: String
        ) : SearchUiState
    }
}

private const val SEARCH_QUERY = "searchQuery"