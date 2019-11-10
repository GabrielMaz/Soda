package com.gabrielmaz.soda.presentation.view.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Movie

class MovieDetailActivity : AppCompatActivity(), MovieDetailFragment.OnFragmentInteractionListener {

    private lateinit var movie: Movie

    override fun goToDiscovers() {
        finish()
    }

    private val MOVIE_DETAIL_FRAGMENT_TAG = "MOVIE_DETAIL_FRAGMENT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra("Movie")

        showDetailFragment()
    }

    private fun removeActiveFragment() {

        listOf(
            MOVIE_DETAIL_FRAGMENT_TAG
        ).forEach { tag ->
            val fragment = supportFragmentManager.findFragmentByTag(tag)
            fragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .remove(it)
                    .commit()
            }
        }
    }

    private fun showDetailFragment() {
        supportFragmentManager.popBackStack()
        removeActiveFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, MovieDetailFragment.newInstance(movie), MOVIE_DETAIL_FRAGMENT_TAG)
            .commit()
    }
}
