package ru.testproject.androidacademy.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.data.getMoviesList


class ViewModelMovieDetails : ViewModel() {
    private var _movieLiveData: MutableLiveData<Movie> = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie> get() = _movieLiveData

    private var _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: MutableLiveData<Boolean> get() = _loadingLiveData

    @ExperimentalSerializationApi
    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            val movies = getMoviesList()
            movies.singleOrNull{it.id == movieId}.let {
                _movieLiveData.value = it
            }
            _loadingLiveData.value = false
        }
    }
}