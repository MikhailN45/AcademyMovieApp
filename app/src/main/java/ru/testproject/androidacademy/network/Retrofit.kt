package ru.testproject.androidacademy.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import kotlin.math.log

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "bb06e6e799f240a9d3fe8b2df3de2e58"
const val LANG = "en-Us"

@ExperimentalSerializationApi
object Retrofit {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(DefaultsInterceptor())
        .addInterceptor(logging)
        .build()

    private val instance: Retrofit = Retrofit
        .Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val service: TheMovieDbApi = instance.create(TheMovieDbApi::class.java)
}

internal class DefaultsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .setQueryParameter("api_key", API_KEY)
            .setQueryParameter("language", LANG)
            .build()
        val enrichedRequest = originalRequest.newBuilder().url(url).build()
        return chain.proceed(enrichedRequest)
    }
}
