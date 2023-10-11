package com.ariabagas.mandiriflix.ui.screen.seeall

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.ariabagas.mandiriflix.core.model.Movie
import com.ariabagas.mandiriflix.core.usecase.GetByGenre
import com.ariabagas.mandiriflix.core.usecase.GetList
import com.ariabagas.mandiriflix.core.usecase.GetSearchResults
import com.ariabagas.mandiriflix.ui.base.BaseViewModel
import com.ariabagas.mandiriflix.ui.screen.UiState
import com.ariabagas.mandiriflix.util.Content
import com.ariabagas.mandiriflix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getList: GetList,
    private val getSearchResults: GetSearchResults,
    private val getByGenre: GetByGenre
) : BaseViewModel() {

    private val _results = MutableStateFlow(setOf<Movie>())
    val results get() = _results.asStateFlow()

    private var contentType: Parcelable? = null
    private var detailType: Parcelable? = null
    private var genreId: Int? = null
    private var listId: String? = null
    private var region: String? = null

    private var page = 1

    private fun fetchList() {
        viewModelScope.launch {
            getList(
                listId = listId!!, page = page, region = region
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += response.data.results

                        _uiState.value = UiState.successState()
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    private fun fetchSearchResults() {
        viewModelScope.launch {
            getSearchResults(
                query = listId!!, page = page
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += response.data.results

                        _uiState.value = UiState.successState()
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    private fun fetchGenreResults() {
        viewModelScope.launch {
            getByGenre(
                genreId = genreId!!, page = page
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += response.data.results
                        _uiState.value = UiState.successState()
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    fun onLoadMore() {
        _uiState.value = UiState.loadingState()
        page++

        when (contentType) {
            Content.EXPLORE_LIST -> fetchList()
            Content.SEARCH -> fetchSearchResults()
            Content.GENRE -> fetchGenreResults()
        }
    }

    fun initRequest(
        contentType: Parcelable?,
        detailType: Parcelable?,
        genreId: Int?,
        listId: String?,
        region: String?
    ) {
        this.contentType = contentType
        this.detailType = detailType
        this.genreId = genreId
        this.listId = listId
        this.region = region

        when (contentType) {
            Content.EXPLORE_LIST -> fetchList()
            Content.SEARCH -> fetchSearchResults()
            Content.GENRE -> fetchGenreResults()
            else -> _uiState.value = UiState.successState()
        }
    }
}