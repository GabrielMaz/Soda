package com.gabrielmaz.soda.presentation.view.movie


import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.controllers.RetrofitController
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.models.Review
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var movie: Movie

    private val movieDetailViewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = arguments?.getParcelable(selectedMovieTag)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetailViewModel.reviews.observe(viewLifecycleOwner, Observer(this::reviewsSize))
        movieDetailViewModel.isFavorite.observe(
            viewLifecycleOwner,
            Observer(this::isFavoriteChanged)
        )

        activity?.let {
            Glide
                .with(it)
                .load("${RetrofitController.baseImageUrl}${movie.posterPath}")
                .centerCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(movie_image)
        }

        movie_title.text = movie.title
        movie_rate.text = movie.voteAverage.toString()
        movie_year.text = movie.releaseDate.subSequence(0, 4)
        movie_description.text = movie.overview

        movieDetailViewModel.setMovie(movie)
        movie_favorite_button.setOnClickListener {
            movieDetailViewModel.toggleFavorite(movie)

        }

        movie_reviews.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        movie_reviews.setOnClickListener {
            listener?.goToReviews()
        }
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
        fun goToReviews()
    }

    private fun isFavoriteChanged(isFavorite: Boolean) {
        movie_favorite_button.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_red else R.drawable.ic_favorite
        )
    }

    private fun reviewsSize(reviews: ArrayList<Review>) {
        val reviewsText = "Reviews (${reviews.size})"
        movie_reviews.text = reviewsText
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
