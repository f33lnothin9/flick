package ru.resodostudios.flick.feature.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.resodostudios.flick.core.data.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    searchRepository: SearchRepository
) : ViewModel()  {

    
}