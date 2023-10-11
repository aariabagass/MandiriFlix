package com.ariabagas.mandiriflix.data.network.responses


import com.google.gson.annotations.SerializedName

data class CompanyDTO(
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String
)