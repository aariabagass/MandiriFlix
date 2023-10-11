package com.ariabagas.mandiriflix.ui.screen.search

import androidx.lifecycle.viewModelScope
import com.ariabagas.mandiriflix.core.model.Movie
import com.ariabagas.mandiriflix.core.usecase.GetSearchResults
import com.ariabagas.mandiriflix.ui.base.BaseViewModel
import com.ariabagas.mandiriflix.ui.screen.UiState
import com.ariabagas.mandiriflix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchResults: GetSearchResults) :
    BaseViewModel() {

    private val _isSearchIsInitialized = MutableStateFlow(false)
    val isSearchIsInitialized get() = _isSearchIsInitialized.asStateFlow()

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _movieResults = MutableStateFlow(emptyList<Movie>())

    val movieResult get() = _movieResults.asStateFlow()

    private val _movieTotalResult = MutableStateFlow(0)
    val movieTotalResult get() = _movieTotalResult.asStateFlow()

    private var page = 1
    private var isQueryChanged = false

    fun fetchInitialSearch(query: String) {
        _uiState.value = UiState.loadingState(isInitial)
        _isSearchIsInitialized.value = true
        _query.value = query

        isQueryChanged = true
        isInitial = true

        page = 1

        initRequests()
    }

    private suspend fun fetchResults() {
        getSearchResults(query = query.value, page = page).collect { response ->
            when (response) {
                is Resource.Success -> {
                    with(response.data) {
                        _movieResults.value =
                            if (isQueryChanged) results else _movieResults.value + results
                        _movieTotalResult.value = totalResults
                    }

                    areResponsesSuccessful.add(true)
                    isInitial = false
                }

                is Resource.Error -> {
                    errorText = response.message
                    areResponsesSuccessful.add(false)
                }
            }
        }
    }

    fun clearSearchResults() {
        _isSearchIsInitialized.value = false
        _query.value = ""
        _movieResults.value = emptyList()
    }

    fun initRequests() {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    fetchResults()
                }
            }
            setUiState()
        }
    }

    fun onLoadMore() {
        _uiState.value = UiState.loadingState(isInitial)
        isQueryChanged = false

        page++

        viewModelScope.launch {
            coroutineScope {
                fetchResults()
            }
            setUiState()
        }
    }
}