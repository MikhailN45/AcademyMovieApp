package ru.testproject.androidacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.testproject.androidacademy.data.Genre
import ru.testproject.androidacademy.data.Movie

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
    private val movieBackground: ImageView = movieItem.findViewById(R.id.movieBackground)
    private val ageRating: ImageView = movieItem.findViewById(R.id.age_rating)
    private val like: ImageView = movieItem.findViewById(R.id.like)
    private val movieTitle: TextView = movieItem.findViewById(R.id.card_name)
    private val movieLength: TextView = movieItem.findViewById(R.id.minutes)
    private val movieRating: RatingBar = movieItem.findViewById(R.id.ratingBar)
    private val reviewsCount: TextView = movieItem.findViewById(R.id.reviews)
    private val tags: TextView = movieItem.findViewById(R.id.tagLine)
    private val clickItem: View = movieItem.findViewById(R.id.movieClick)


    fun bind(movie: Movie) {
        Glide
            .with(movieItem)
            .load(movie.poster)
            .into(movieBackground)

        ageRating.setImageResource(getAgeRatingImg(movie.minimumAge))
        like.setImageResource(R.drawable.like)
        movieTitle.text = movie.title
        movieRating.rating = convertRating(movie.ratings)
        tags.text = getTags(movie.genres)
        reviewsCount.text = "${movie.numberOfRatings} REVIEWS"
        movieLength.text = "${movie.runtime} MIN"

        clickItem.setOnClickListener { movieClickListener.onMovieClick(movie) }
    }

    private fun getTags(genres: List<Genre>): String = genres.joinToString(", ") { it.name }

    private fun convertRating(ratings: Float): Float = ratings / 2.0f

    private fun getAgeRatingImg(minimumAge: Int): Int =
        if (minimumAge < 16) R.drawable.rating16 else R.drawable.rating13
}

