package ru.testproject.androidacademy


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.data.MoviesData

class FragmentMoviesList : Fragment(), AdapterMovieList.MovieClickListener {
    private var movies: List<MoviesData> = listOf(
        MoviesData(
            R.drawable.avengers_small,
            R.drawable.rating13,
            R.drawable.like,
            "Avengers: End Game",
            137,
            4,
            125,
            listOf("Action", "Adventure", "Fantasy")
        ),
        MoviesData(
            R.drawable.tenet,
            R.drawable.rating16,
            R.drawable.like,
            "Tenet",
            97,
            5,
            98,
            listOf("Action", "Sci-Fi", "Thriller")
        ),
        MoviesData(
            R.drawable.black_widow,
            R.drawable.rating13,
            R.drawable.like,
            "Black Widow",
            102,
            4,
            38,
            listOf("Action", "Adventure", "Sci-Fi")
        ),
        MoviesData(
            R.drawable.superwoman,
            R.drawable.rating13,
            R.drawable.like,
            "Wonder Woman",
            120,
            5,
            74,
            listOf("Action", "Adventure", "Fantasy")
        )
    )

    companion object {
        const val TAG = "MovieListFragment"
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_rv)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = AdapterMovieList(this)
        (recyclerView.adapter as AdapterMovieList).setMovie(movies)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMovieClick(movie: MoviesData) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentContainer,
                FragmentMoviesDetails.newInstance(),
                FragmentMoviesDetails.TAG
            )
            .addToBackStack(FragmentMoviesDetails.TAG)
            .commit()
    }
}
