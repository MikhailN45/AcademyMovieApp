package ru.testproject.androidacademy.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.AdapterMovieDetails
import ru.testproject.androidacademy.R
import ru.testproject.androidacademy.data.Genre
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.viewmodels.ViewModelMovieDetails
import coil.load


class FragmentMoviesDetails : Fragment() {

    private var onBackButtonCL: MovieDetailsCL? = null

    private val movieDetailsViewModel: ViewModelMovieDetails by viewModels()

    private var recyclerView: RecyclerView? = null
    private var poster: ImageView? = null
    private var title: TextView? = null
    private var ageRating: TextView? = null
    private var storyLine: TextView? = null
    private var backButton: TextView? = null
    private var tagLine: TextView? = null
    private var reviewsCount: TextView? = null
    private var ratingBar: RatingBar? = null


    companion object {
        const val TAG = "MovieDetailsFragment"
        private const val MOVIE_ID = "movieId"

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, movieId)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = arguments?.getInt(MOVIE_ID)
        initViews(view)
        movieDetailsViewModel.getMovie(movieId!!)
        backButton?.setOnClickListener { onBackButtonCL?.onBackClick() }

        recyclerView?.let {
            it.adapter = AdapterMovieDetails()
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        movieDetailsViewModel.movieLiveData.observe(viewLifecycleOwner) { movie: Movie ->
            poster?.load(movie.backdrop)
            title?.text = movie.title
            ageRating?.text = getAgeRating(movie.minimumAge)
            storyLine?.text = movie.overview
            ratingBar?.rating = convertRating(movie.ratings)
            tagLine?.text = getTags(movie.genres)
            val reviewsCountText = "${movie.numberOfRatings} REVIEWS"
            reviewsCount?.text = reviewsCountText
            if (movie.actors.isEmpty()) view.findViewById<TextView>(R.id.cast_title).visibility =
                View.GONE
            (recyclerView?.adapter as AdapterMovieDetails).updateActors(movie.actors)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getTags(genres: List<Genre>): String = genres.joinToString(", ") { it.name }

    private fun convertRating(incomeRating: Float): Float = incomeRating / 2.0f

    private fun getAgeRating(minimumAge: Int): String {
        val setAgeView: Int =
            if (minimumAge < 16) R.string.age_rating_13 else R.string.age_rating_16
        return getString(setAgeView)
    }

    override fun onAttach(context: Context) {
        if (context is MovieDetailsCL) {
            onBackButtonCL = context
        }
        super.onAttach(context)
    }

    interface MovieDetailsCL {
        fun onBackClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
        poster = null
        title = null
        tagLine = null
        ageRating = null
        storyLine = null
        backButton = null
        ratingBar = null
        reviewsCount = null
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.actors_rv)
        poster = view.findViewById(R.id.film_poster)
        title = view.findViewById(R.id.movie_title)
        tagLine = view.findViewById(R.id.movie_genres)
        ageRating = view.findViewById(R.id.age_rating)
        storyLine = view.findViewById(R.id.storyline_tv)
        backButton = view.findViewById(R.id.back_button_text)
        ratingBar = view.findViewById(R.id.movie_rating_bar)
        reviewsCount = view.findViewById(R.id.reviews_movie_card)
    }
}