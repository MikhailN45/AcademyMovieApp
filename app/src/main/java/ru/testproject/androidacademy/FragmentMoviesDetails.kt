package ru.testproject.androidacademy

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.data.ActorsData

class FragmentMoviesDetails : Fragment() {
    private val actors: List<ActorsData> = listOf(
        ActorsData(
            R.drawable.downey,
            "Robert Downey Jr."
        ),
        ActorsData(
            R.drawable.evans,
            "Chris \nEvans"
        ),
        ActorsData(
            R.drawable.ruffalo,
            "Mark Ruffalo"
        ),
        ActorsData(
            R.drawable.hemsworth,
            "Chris Hemsworth"
        )
    )

    private var onBackButtonCL: MovieDetailsCL? = null

    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "MovieDetailsFragment"
        fun newInstance(): FragmentMoviesDetails = FragmentMoviesDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.back_button_text)
            .setOnClickListener { onBackButtonCL?.onBackClick() }
        recyclerView= view.findViewById(R.id.actors_rv)
        recyclerView.adapter = AdapterMovieDetails()
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        (recyclerView.adapter as AdapterMovieDetails).updateActors(actors)

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MovieDetailsCL) {
            onBackButtonCL = context
        }
    }

    interface MovieDetailsCL {
        fun onBackClick()
    }
}