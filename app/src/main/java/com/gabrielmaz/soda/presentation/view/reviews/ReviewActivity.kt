package com.gabrielmaz.soda.presentation.view.reviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielmaz.soda.R

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val movieId = intent.getIntExtra(MOVIE_ID_TAG, 0)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, ReviewFragment.newInstance(movieId))
            .commit()
    }

    companion object {
        const val MOVIE_ID_TAG = "MOVIE_ID_TAG"
    }
}
