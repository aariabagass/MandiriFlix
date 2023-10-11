package com.ariabagas.mandiriflix.data.network.responses


import com.google.gson.annotations.SerializedName

data class MovieReviewListDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movieReviewDTOS: List<MovieReviewDTO>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieReviewDTO(
    @SerializedName("author") val author: String,
    @SerializedName("author_details") val authorDetailsDTO: AuthorDetailsDTO,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("url") val url: String
)

data class AuthorDetailsDTO(
    @SerializedName("avatar_path") val avatarPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("username") val username: String
)