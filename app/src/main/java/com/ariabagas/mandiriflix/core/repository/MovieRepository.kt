package com.ariabagas.mandiriflix.core.repository

import com.ariabagas.mandiriflix.core.model.MovieDetail
import com.ariabagas.mandiriflix.core.model.MovieList
import com.ariabagas.mandiriflix.core.model.MovieReviewList
import com.ariabagas.mandiriflix.util.Resource

interface MovieRepository {
    suspend fun getMovieList(listId: String, page: Int, region: String?): Resource<MovieList>
    suspend fun getMoviesByGenre(genreId: Int, page: Int): Resource<MovieList>
    suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList>
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail>
    suspend fun getMovieReviewList(movieId: Int): Resource<MovieReviewList>

}