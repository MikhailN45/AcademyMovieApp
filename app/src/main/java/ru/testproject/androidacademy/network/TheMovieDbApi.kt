package ru.testproject.androidacademy.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.testproject.androidacademy.tmdb.*


interface TheMovieDbApi {
    @GET("configuration")
    suspend fun getConfiguration(): Configuration

    @GET("movie/now_playing?page=1")
    suspend fun getMovies(): Movies

    @GET("genre/movie/list")
    suspend fun getGenres(): Genres

    @GET("movie/{id}/credits")
    suspend fun getCredits(@Path("id") movieId: Int): Credits

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") movieId: Int): Details
}