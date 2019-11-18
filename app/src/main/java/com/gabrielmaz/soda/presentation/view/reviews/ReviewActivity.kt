package com.gabrielmaz.soda.presentation.view.reviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.presentation.view.movie.MovieDetailActivity

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val movie = intent.getParcelableExtra<Movie>(MOVIE_TAG)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, ReviewFragment.newInstance(movie))
                .commit()
        }
    }

    companion object {
        const val MOVIE_TAG = "MOVIE"
    }
}
