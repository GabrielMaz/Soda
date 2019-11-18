package com.gabrielmaz.soda.presentation.view.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.presentation.view.reviews.ReviewActivity

class MovieDetailActivity : AppCompatActivity(), MovieDetailFragment.OnFragmentInteractionListener {
    private lateinit var movie: Movie

    override fun goToReviews() {
        val intent = Intent(this, ReviewActivity::class.java)
        intent.putExtra(MOVIE_TAG, movie)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra(MOVIE_TAG)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, MovieDetailFragment.newInstance(movie))
                .commit()
        }
    }

    companion object {
        const val MOVIE_TAG = "MOVIE_TAG"
    }
}
