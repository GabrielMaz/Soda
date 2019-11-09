package com.gabrielmaz.soda.presentation.view.movie


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.todo_lo_otro.controllers.RetrofitController
import com.gabrielmaz.soda.todo_lo_otro.models.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = arguments?.getParcelable(selectedMovieTag)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            Glide
                .with(it)
                .load("${RetrofitController.baseImageUrl}${movie?.posterPath}")
                .centerCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(movie_image)
        }

        movie_title.text = movie?.title
        movie_rate.text = movie?.voteAverage.toString()
        movie_year.text = movie?.releaseDate?.subSequence(0, 4)
        movie_description.text = movie?.overview

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun goToDiscovers()
    }

    companion object {
        const val selectedMovieTag = "selectedMovieTag"

        @JvmStatic
        fun newInstance(selectedMovie: Movie?) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(selectedMovieTag, selectedMovie)
                }
            }
    }
}
