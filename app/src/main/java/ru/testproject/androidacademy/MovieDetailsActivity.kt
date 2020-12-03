package ru.testproject.androidacademy

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val backToMainActivity: TextView = findViewById(R.id.back_button_text)
        backToMainActivity.setOnClickListener { moveToTheMain() }
    }

    private fun moveToTheMain() {
        val backToTheMainIntent = Intent(this, MainActivity::class.java)
        startActivity(backToTheMainIntent)
    }
}