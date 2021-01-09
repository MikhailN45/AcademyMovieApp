package ru.testproject.androidacademy

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.testproject.androidacademy.data.Movie


class FragmentMoviesDetails : Fragment() {

    private lateinit var movie: Movie

    private var onBackButtonCL: MovieDetailsCL? = null

    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "MovieDetailsFragment"

        fun newInstance(bundle: Bundle): FragmentMoviesDetails {
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

        val title = view.findViewById<TextView>(R.id.movie_title)
        val poster = view.findViewById<ImageView>(R.id.film_poster)
        val ageRating = view.findViewById<TextView>(R.id.age_rating)
        val storyLine = view.findViewById<TextView>(R.id.storyline_tv)
        val movieId = arguments?.getInt(FragmentMoviesList.MOVIE_ID)
        movie = MainActivity.movies.single { it.id == movieId }
        view.findViewById<TextView>(R.id.back_button_text).setOnClickListener { onBackButtonCL?.onBackClick() }

        recyclerView = view.findViewById(R.id.actors_rv)
        recyclerView.adapter = AdapterMovieDetails()
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        (recyclerView.adapter as AdapterMovieDetails).updateActors(movie.actors)
        if (movie.actors.isEmpty()) view.findViewById<TextView>(R.id.cast_title).visibility =
            View.GONE

        Glide.with(view).load(movie.backdrop).into(poster)
        title.text = movie.title
        ageRating.text = getAgeRating(movie.minimumAge)
        storyLine.text = movie.overview

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getAgeRating(minimumAge: Int): String {
        val setAgeView: Int = if (minimumAge < 16) R.string.age_rating_13 else R.string.age_rating_16
        return getString(setAgeView)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieDetailsCL) {
            onBackButtonCL = context
        }
    }

    interface MovieDetailsCL {
        fun onBackClick()
    }
}