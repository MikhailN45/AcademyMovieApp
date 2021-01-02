package ru.testproject.androidacademy

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentMoviesList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater?.inflate(R.layout.fragment_movies_list, container, false)
}
