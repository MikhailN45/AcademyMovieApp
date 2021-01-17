package ru.testproject.androidacademy.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.data.loadMovies

class ViewModelMovieDetails(private val app: Application) : AndroidViewModel(app) {
    private var _movieLiveData: MutableLiveData<Movie> = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie>
        get() = _movieLiveData

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            val movies = loadMovies(app.applicationContext)
            val movie = movies.single { it.id == movieId }
            _movieLiveData.postValue(movie)
        }
    }
}