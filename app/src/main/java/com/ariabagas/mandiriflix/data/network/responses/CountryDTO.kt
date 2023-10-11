package com.ariabagas.mandiriflix.data.network.responses


import com.google.gson.annotations.SerializedName

data class CountryDTO(
    @SerializedName("name")
    val name: String
)