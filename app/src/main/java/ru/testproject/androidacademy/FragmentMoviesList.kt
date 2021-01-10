package ru.testproject.androidacademy


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.data.Movie

class FragmentMoviesList : Fragment(), AdapterMovieList.MovieClickListener {
    private var movies: List<Movie> = listOf()

    companion object {
        const val TAG = "MovieListFragment"
        const val MOVIE_ID = "movieId"
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movies = MainActivity.movies
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_rv)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = AdapterMovieList(this)
        (recyclerView.adapter as AdapterMovieList).setMovie(movies)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMovieClick(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movie.id)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentContainer,
                FragmentMoviesDetails.newInstance(bundle),
                FragmentMoviesDetails.TAG
            )
            .addToBackStack(FragmentMoviesDetails.TAG)
            .commit()
    }
}
