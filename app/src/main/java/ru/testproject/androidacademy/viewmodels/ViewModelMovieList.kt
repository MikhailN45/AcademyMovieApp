package ru.testproject.androidacademy.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.data.getMoviesList

class ViewModelMovieList:ViewModel() {
    private var _movieListLiveData: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val movieListLiveData: LiveData<List<Movie>>
        get() = _movieListLiveData
    private var _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    @ExperimentalSerializationApi
    fun getMovies() {
        viewModelScope.launch {
            _loadingLiveData.value = true
            _movieListLiveData.value = getMoviesList()
            _loadingLiveData.value = false
        }
    }
}