package ru.testproject.androidacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.testproject.androidacademy.data.Actor
import ru.testproject.androidacademy.databinding.ViewHolderActorBinding

class AdapterMovieDetails : RecyclerView.Adapter<MovieDetailsViewHolder>() {
    private var actors: List<Actor> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsViewHolder {
        return MovieDetailsViewHolder(
            listItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MovieDetailsViewHolder,
        position: Int
    ) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int = actors.size


    fun updateActors(newActors: List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

class MovieDetailsViewHolder(private val listItem: View) : RecyclerView.ViewHolder(listItem) {
    private val binding = ViewHolderActorBinding.bind(listItem)

    fun bind(actors: Actor) {
        binding.actorImage.load(actors.picture)
        binding.actorName.text = actors.name
    }
}