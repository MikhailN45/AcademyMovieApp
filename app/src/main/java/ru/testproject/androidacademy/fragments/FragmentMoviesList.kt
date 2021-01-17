package ru.testproject.androidacademy.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.AdapterMovieList
import ru.testproject.androidacademy.R
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.viewmodels.ViewModelMovieList

class FragmentMoviesList : Fragment(), AdapterMovieList.MovieClickListener {
    private var recyclerView: RecyclerView? = null
    private var progressBar: View? = null
    private val movieListViewModel: ViewModelMovieList by viewModels()

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
        initViews(view)
        movieListViewModel.getMovies()
        recyclerView?.let {
            it.layoutManager = GridLayoutManager(requireContext(), 2)
            it.adapter = AdapterMovieList(this)
        }
        movieListViewModel.movieListLiveData.observe(viewLifecycleOwner) {
            (recyclerView?.adapter as AdapterMovieList).setMovie(it)
        }
        movieListViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            progressBar?.visibility = if (it) View.VISIBLE else View.GONE
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        recyclerView = null
        progressBar = null
        super.onDestroyView()
    }

    override fun onMovieClick(movie: Movie) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentContainer,
                FragmentMoviesDetails.newInstance(movie.id),
                FragmentMoviesDetails.TAG
            )
            .addToBackStack(FragmentMoviesDetails.TAG)
            .commit()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.movies_rv)
        progressBar = view.findViewById(R.id.progressBar)
    }
}
