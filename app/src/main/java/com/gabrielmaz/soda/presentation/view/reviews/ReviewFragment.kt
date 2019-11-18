package com.gabrielmaz.soda.presentation.view.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.models.Review
import com.gabrielmaz.soda.presentation.view.movie.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewFragment : Fragment() {
    private lateinit var adapter: ReviewAdapter
    private val reviewViewModel: ReviewViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments?.getParcelable<Movie>(MovieDetailActivity.MOVIE_TAG)

        reviews_title.text = movie?.title ?: ""
        adapter = ReviewAdapter()
        reviews_list.layoutManager = LinearLayoutManager(activity)
        reviews_list.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        reviews_list.adapter = adapter

        if (movie != null) {
            reviewViewModel.loadReviews(movie.id)
        }

        reviewViewModel.reviews.observe(viewLifecycleOwner, Observer(this::reviewsChanged))
    }

    private fun reviewsChanged(reviews: ArrayList<Review>) {
        adapter.reviews = reviews
        reviews_count.text = "Reviews (${reviews.size})"
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie?) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    if (movie != null) {
                        putParcelable(MovieDetailActivity.MOVIE_TAG, movie)
                    }
                }
            }
    }
}
