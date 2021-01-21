package ru.testproject.androidacademy.tmdb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Details(
    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genres")
    val genres: List<Genre>,

    @SerialName("id")
    val id: Int,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("runtime")
    val runtime: Int,

    @SerialName("title")
    val title: String,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("vote_count")
    val voteCount: Int
)