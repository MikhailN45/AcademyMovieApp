package ru.testproject.androidacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.testproject.androidacademy.data.Actor
import com.bumptech.glide.Glide

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
    private val actorName: TextView = listItem.findViewById(R.id.actorName)
    private val actorImage: ImageView = listItem.findViewById(R.id.actorImage)

    fun bind(actors: Actor) {
        actorName.text = actors.name
        Glide.with(listItem).load(actors.picture).into(actorImage)
    }
}