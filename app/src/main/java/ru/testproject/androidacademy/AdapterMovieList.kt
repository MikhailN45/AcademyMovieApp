package ru.testproject.androidacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.data.MoviesData

class AdapterMovieList(private val movieClickListener: MovieClickListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var movieList: List<MoviesData> = listOf()

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
        fun onMovieClick(movie: MoviesData)
    }

    fun setMovie(newMovie: List<MoviesData>) {
        movieList = newMovie
        notifyDataSetChanged()
    }

}

class MovieViewHolder(
    movieItem: View,
    private val movieClickListener: AdapterMovieList.MovieClickListener
) : RecyclerView.ViewHolder(movieItem) {
    private val movieBackground: ImageView = movieItem.findViewById(R.id.movieBackground)
    private val pgRating: ImageView = movieItem.findViewById(R.id.age_rating)
    private val like: ImageView = movieItem.findViewById(R.id.like)
    private val movieTitle: TextView = movieItem.findViewById(R.id.card_name)
    private val movieLength: TextView = movieItem.findViewById(R.id.minutes)
    private val movieRating: RatingBar = movieItem.findViewById(R.id.ratingBar)
    private val reviewsCount: TextView = movieItem.findViewById(R.id.reviews)
    private val tags: TextView = movieItem.findViewById(R.id.tagLine)
    private val clickItem: View = movieItem.findViewById(R.id.movieClick)


    fun bind(movieData: MoviesData) {
        movieBackground.setImageResource(movieData.poster)
        pgRating.setImageResource(movieData.ageRating)
        like.setImageResource(movieData.like)
        movieTitle.text = movieData.title
        movieRating.rating = movieData.userRating.toFloat()
        tags.text = movieData.genres.joinToString(", ")
        reviewsCount.text = "${movieData.reviews} REVIEWS"
        movieLength.text = "${movieData.length} MIN"

        clickItem.setOnClickListener { movieClickListener.onMovieClick(movieData) }
    }
}

