package com.gabrielmaz.soda.presentation.view.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Review
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : Fragment() {
    private lateinit var adapter: ReviewAdapter
    private val reviewViewModel = ReviewViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt(ReviewActivity.MOVIE_ID_TAG)

        adapter = ReviewAdapter()
        reviews_list.layoutManager = LinearLayoutManager(activity)
        reviews_list.adapter = adapter

        if (movieId != null) {
            reviewViewModel.loadReviews(movieId)
        }

        reviewViewModel.reviews.observe(viewLifecycleOwner, Observer(this::reviewsChanged))
    }

    private fun reviewsChanged(reviews: ArrayList<Review>) {
        adapter.reviews = reviews
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int?) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    if (movieId != null) {
                        putInt(ReviewActivity.MOVIE_ID_TAG, movieId)
                    }
                }
            }
    }
}
