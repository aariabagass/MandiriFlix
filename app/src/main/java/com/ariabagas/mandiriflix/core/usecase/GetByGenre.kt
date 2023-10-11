package com.ariabagas.mandiriflix.core.usecase

import com.ariabagas.mandiriflix.core.model.MovieList
import com.ariabagas.mandiriflix.core.repository.MovieRepository
import com.ariabagas.mandiriflix.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetByGenre @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(
        genreId: Int, page: Int
    ): Flow<Resource<MovieList>> = flow {
        emit(
            movieRepository.getMoviesByGenre(genreId = genreId, page = page)
        )
    }
}