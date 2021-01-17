package ru.testproject.androidacademy.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.data.loadMovies

class ViewModelMovieList(private val app: Application) : AndroidViewModel(app) {
    private var _movieListLiveData: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val movieListLiveData: LiveData<List<Movie>>
        get() = _movieListLiveData
    private var _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    fun getMovies() {
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            _movieListLiveData.postValue(loadMovies(app.applicationContext))
            _loadingLiveData.postValue(false)
        }
    }
}