package com.ariabagas.mandiriflix.data.repository

import com.ariabagas.mandiriflix.data.mapper.toMovieDetail
import com.ariabagas.mandiriflix.data.mapper.toMovieList
import com.ariabagas.mandiriflix.data.mapper.toMovieReviewList
import com.ariabagas.mandiriflix.data.network.services.MovieApi
import com.ariabagas.mandiriflix.core.model.MovieDetail
import com.ariabagas.mandiriflix.core.model.MovieList
import com.ariabagas.mandiriflix.core.model.MovieReviewList
import com.ariabagas.mandiriflix.core.repository.MovieRepository
import com.ariabagas.mandiriflix.util.Resource
import com.ariabagas.mandiriflix.util.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi, private val safeApiCall: SafeApiCall
) : MovieRepository {
    override suspend fun getMovieList(
        listId: String, page: Int, region: String?
    ): Resource<MovieList> = safeApiCall.execute {
        movieApi.getMovieList(listId = listId, page = page, region = region).toMovieList()
    }

    override suspend fun getMoviesByGenre(genreId: Int, page: Int): Resource<MovieList> =
        safeApiCall.execute {
            movieApi.getMoviesByGenre(genreId = genreId, page = page).toMovieList()
        }

    override suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList> =
        safeApiCall.execute {
            movieApi.getMovieSearchResults(query = query, page = page).toMovieList()
        }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> =
        safeApiCall.execute {
            movieApi.getMovieDetails(movieId = movieId).toMovieDetail()
        }

    override suspend fun getMovieReviewList(movieId: Int): Resource<MovieReviewList> =
        safeApiCall.execute {
            movieApi.getMovieReviews(movieId).toMovieReviewList()
        }

}