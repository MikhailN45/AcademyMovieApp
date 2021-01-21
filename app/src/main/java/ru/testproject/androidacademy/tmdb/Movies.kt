package ru.testproject.androidacademy.tmdb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    @SerialName("page")
    val page: Long,

    @SerialName("results")
    val results: List<Result>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)

@Serializable
data class Result(
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genre_ids")
    val genreIDS: List<Int>,

    @SerialName("id")
    val id: Int,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("title")
    val title: String,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("vote_count")
    val voteCount: Int
)