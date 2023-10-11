package com.ariabagas.mandiriflix.core.usecase

import com.ariabagas.mandiriflix.core.repository.MovieRepository
import com.ariabagas.mandiriflix.util.Constants
import com.ariabagas.mandiriflix.util.Resource
import com.ariabagas.mandiriflix.util.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetails @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(type: Type, id: Int): Flow<Resource<Any>> = flow {
        emit(
            when (type) {
                Type.MOVIE -> movieRepository.getMovieDetails(id)
                Type.REVIEW -> movieRepository.getMovieReviewList(id)
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
            }
        )
    }
}