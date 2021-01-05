package ru.testproject.androidacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.data.ActorData

class AdapterMovieDetails : RecyclerView.Adapter<MovieDetailsViewHolder>() {
    private var actors: List<ActorData> = listOf()

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


    fun updateActors(newActors: List<ActorData>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

class MovieDetailsViewHolder(listItem: View) : RecyclerView.ViewHolder(listItem) {
    private val actorName: TextView = listItem.findViewById(R.id.actorName)
    private val actorImage: ImageView = listItem.findViewById(R.id.actorImage)

    fun bind(actors: ActorData) {
        actorImage.setImageResource(actors.actorImage)
        actorName.text = actors.actorName
    }
}