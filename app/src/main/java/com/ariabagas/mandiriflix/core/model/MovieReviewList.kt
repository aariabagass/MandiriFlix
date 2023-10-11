package com.ariabagas.mandiriflix.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MovieReviewList(
    val results: List<MovieReview>, val totalResults: Int
) {
    companion object {
        val empty = MovieReviewList(
            results = emptyList(), totalResults = 0
        )
    }
}

@Parcelize
data class MovieReview(
    val authorName: String,
    val content: String,
    val id: String,
    val createdAt: String,
    val avatarUrl: String? = null
) : Parcelable
