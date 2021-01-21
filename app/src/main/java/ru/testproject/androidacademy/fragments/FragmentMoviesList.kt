package ru.testproject.androidacademy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import ru.testproject.androidacademy.R
import ru.testproject.androidacademy.adapters.AdapterMovieList
import ru.testproject.androidacademy.data.Movie
import ru.testproject.androidacademy.data.getMoviesList
import ru.testproject.androidacademy.databinding.FragmentMovieListBinding
import ru.testproject.androidacademy.viewmodels.ViewModelMovieList

class FragmentMoviesList : Fragment(), AdapterMovieList.MovieClickListener {
    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding!!
    private val movieListViewModel: ViewModelMovieList by viewModels()

    companion object {
        const val TAG = "MovieListFragment"
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = getMoviesList()
        }
        movieListViewModel.getMovies()
        binding.moviesRv.let {
            it.layoutManager = GridLayoutManager(requireContext(), 2)
            it.adapter = AdapterMovieList(this)
        }
        movieListViewModel.movieListLiveData.observe(viewLifecycleOwner) {
            (binding.moviesRv.adapter as AdapterMovieList).setMovie(it)
        }
        movieListViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
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
}
