package com.ariabagas.mandiriflix.ui.screen.moviedetails

import androidx.lifecycle.viewModelScope
import com.ariabagas.mandiriflix.core.model.MovieDetail
import com.ariabagas.mandiriflix.core.model.MovieReviewList
import com.ariabagas.mandiriflix.core.usecase.GetDetails
import com.ariabagas.mandiriflix.ui.base.BaseViewModel
import com.ariabagas.mandiriflix.ui.screen.UiState
import com.ariabagas.mandiriflix.util.Resource
import com.ariabagas.mandiriflix.util.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val getDetails: GetDetails) :
    BaseViewModel() {

    private val _details = MutableStateFlow(MovieDetail.empty)
    val details get() = _details.asStateFlow()

    private val _movieReviews = MutableStateFlow(MovieReviewList.empty)
    val movieReviews = _movieReviews.asStateFlow()

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            getDetails(Type.MOVIE, detailId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data as MovieDetail).apply {
                            _details.value = this
                        }
                        _uiState.value = UiState.successState()
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    private fun fetchMovieReviews() {
        viewModelScope.launch {
            getDetails(Type.REVIEW, detailId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data as MovieReviewList).apply {
                            _movieReviews.value = this
                        }
                        _uiState.value = UiState.successState()
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    fun initRequest(movieId: Int) {
        detailId = movieId
        fetchMovieDetails()
        fetchMovieReviews()
    }

}