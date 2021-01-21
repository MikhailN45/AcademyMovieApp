package ru.testproject.androidacademy.tmdb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Credits(
    val id: Int,
    val cast: List<Cast>
)
@Serializable
data class Cast(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("cast_id")
    val castID: Int? = null
)