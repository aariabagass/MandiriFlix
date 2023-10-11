package com.ariabagas.mandiriflix.data.network.responses


import com.google.gson.annotations.SerializedName

data class LanguageDTO(
    @SerializedName("english_name")
    val englishName: String
)