package ru.testproject.androidacademy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieDetails: TextView = findViewById(R.id.movie_details_tv)
        movieDetails.setOnClickListener { moveToTheMovieDetailsScreen() }
    }

    private fun moveToTheMovieDetailsScreen() {
        val movieDetailsIntent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(movieDetailsIntent)
    }


}