package ru.testproject.androidacademy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.testproject.androidacademy.R
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.databinding.ViewHolderMovieBinding
import ru.testproject.androidacademy.tmdb.Genre

class AdapterMovieList(private val movieClickListener: MovieClickListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var movieList: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return MovieViewHolder(view, movieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    fun setMovie(newMovie: List<Movie>) {
        movieList = newMovie
        notifyDataSetChanged()
    }
}

class MovieViewHolder(
    private val movieItem: View,
    private val movieClickListener: AdapterMovieList.MovieClickListener
) : RecyclerView.ViewHolder(movieItem) {
    private val binding = ViewHolderMovieBinding.bind(movieItem)

    fun bind(movie: Movie) {
        binding.movieBackground.load(movie.poster)
        binding.ageRating.setImageResource(getAgeRatingImg(movie.minimumAge))
        binding.like.setImageResource(R.drawable.like)
        binding.cardName.text = movie.title
        binding.ratingBar.rating = convertRating(movie.ratings)
        binding.tagLine.text = getTags(movie.genres)
        binding.reviewsMovieCard.text = "${movie.numberOfRatings} REVIEWS"
        binding.minutes.text = "${movie.runtime} MIN"

        binding.movieClick.setOnClickListener { movieClickListener.onMovieClick(movie) }
    }

    private fun getTags(genres: List<Genre>): String = genres.joinToString(", ") { it.name }

    private fun convertRating(ratings: Float): Float = ratings / 2.0f

    private fun getAgeRatingImg(minimumAge: Int): Int =
        if (minimumAge < 16) R.drawable.rating16 else R.drawable.rating13
}

