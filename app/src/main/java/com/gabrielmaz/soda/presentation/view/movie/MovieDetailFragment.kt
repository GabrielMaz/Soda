package com.gabrielmaz.soda.presentation.view.movie


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
import com.gabrielmaz.soda.data.sources.AppDatabase
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.coroutines.Dispatchers

class MovieDetailFragment : Fragment() {
    var movie: Movie? = null

    private val movieDetailViewModel = MovieDetailViewModel()

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
        movie_reviews.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        movie?.let { validMovie ->
            activity?.let { activity ->
                movieDetailViewModel.setMovie(activity, validMovie)
            }
            movie_favorite_button.setOnClickListener {
                activity?.let { activity ->
                    movieDetailViewModel.toggleFavorite(activity, validMovie)
                }
            }
        }

        movie?.id?.let { movieDetailViewModel.loadReviews(it) }
        movieDetailViewModel.reviews.observe(viewLifecycleOwner, Observer(this::reviewsSize))
        movieDetailViewModel.isFavorite.observe(
            viewLifecycleOwner,
            Observer(this::isFavoriteChanged)
        )
    }

    private fun isFavoriteChanged(isFavorite: Boolean) {
        movie_favorite_button.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_red else R.drawable.ic_favorite
        )
    }

    private fun reviewsSize(size: Int) {
        val reviewsText = "Reviews ($size)"
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
