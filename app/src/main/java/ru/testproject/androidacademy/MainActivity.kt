package ru.testproject.androidacademy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.data.loadMovies
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), FragmentMoviesDetails.MovieDetailsCL {

    private lateinit var rootFragment: FragmentMoviesList
    private lateinit var detailsFragment: FragmentMoviesDetails

    companion object {
        var movies: List<Movie> = listOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val coroutine = async(Dispatchers.IO) {
                movies = loadMovies(applicationContext)
            }
            coroutine.await()
        }

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            rootFragment = FragmentMoviesList.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragmentContainer,
                    rootFragment,
                    FragmentMoviesList.TAG
                )
                .commit()
        } else {
            val movieList = supportFragmentManager.findFragmentByTag(FragmentMoviesList.TAG)
            rootFragment = movieList as FragmentMoviesList

            val movieDetails = supportFragmentManager.findFragmentByTag(FragmentMoviesList.TAG)
            if (movieDetails != null) {
                detailsFragment = movieDetails as FragmentMoviesDetails
            }
        }
    }

    override fun onBackClick() {
        onBackPressed()
    }
}