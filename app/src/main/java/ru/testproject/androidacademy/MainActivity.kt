package ru.testproject.androidacademy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movies_list)

        val movieDetails: ImageView = findViewById(R.id.avengers_film_preview)
        movieDetails.setOnClickListener { moveToTheMovieDetailsScreen() }
    }

    private fun moveToTheMovieDetailsScreen() {
        val movieDetailsIntent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(movieDetailsIntent)
    }


}