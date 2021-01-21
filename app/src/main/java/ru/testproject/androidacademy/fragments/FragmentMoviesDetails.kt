package ru.testproject.androidacademy.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.AdapterMovieDetails
import ru.testproject.androidacademy.R
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.viewmodels.ViewModelMovieDetails
import coil.load
import kotlinx.serialization.ExperimentalSerializationApi
import ru.testproject.androidacademy.databinding.FragmentMoviesDetailsBinding
import ru.testproject.androidacademy.tmdb.Genre


class FragmentMoviesDetails : Fragment() {

    private var onBackButtonCL: MovieDetailsCL? = null

    private val movieDetailsViewModel: ViewModelMovieDetails by viewModels()

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding: FragmentMoviesDetailsBinding
        get() = _binding!!

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
    ): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = arguments?.getInt(MOVIE_ID)
        movieDetailsViewModel.getMovie(movieId!!)
        binding.backButtonText.setOnClickListener { onBackButtonCL?.onBackClick() }

        binding.actorsRv.apply {
            adapter = AdapterMovieDetails()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        movieDetailsViewModel.movieLiveData.observe(viewLifecycleOwner) { movie: Movie ->
            binding.filmPoster.load(movie.backdrop)
            binding.movieTitle.text = movie.title
            binding.ageRating.text = getAgeRating(movie.minimumAge)
            binding.storylineTv.text = movie.overview
            binding.movieRatingBar.rating = convertRating(movie.ratings)
            binding.movieGenres.text = getTags(movie.genres)
            val reviewsCountText = "${movie.numberOfRatings} REVIEWS"
            binding.reviewsMovieCard.text = reviewsCountText
            if (movie.actors.isEmpty()) view.findViewById<TextView>(R.id.cast_title).visibility =
                View.GONE
            (binding.actorsRv.adapter as AdapterMovieDetails).updateActors(movie.actors)
        }
        movieDetailsViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            view.findViewById<ProgressBar>(R.id.progressBar).visibility =
                if (it) View.VISIBLE else View.GONE
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
        _binding = null
    }
}