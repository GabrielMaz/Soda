package com.gabrielmaz.soda.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.presentation.view.discover.DiscoverFragment
import com.gabrielmaz.soda.presentation.view.favorites.FavoritesFragment
import com.gabrielmaz.soda.presentation.view.movie.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope,
    DiscoverFragment.OnFragmentInteractionListener,
    FavoritesFragment.OnFragmentInteractionListener {

    override fun goToMovieDetails(selectedMovie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE_TAG, selectedMovie)
        startActivity(intent)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigationBar()

        if (savedInstanceState == null) {
            load(DISCOVER_FRAGMENT_TAG)
        }
    }

    private fun load(fragment: String) {

        when (fragment) {
            DISCOVER_FRAGMENT_TAG -> showFragment(
                DiscoverFragment(),
                DISCOVER_FRAGMENT_TAG
            )
            FAVORITES_FRAGMENT_TAG -> showFragment(
                FavoritesFragment(),
                FAVORITES_FRAGMENT_TAG
            )

            else -> showFragment(DiscoverFragment(), DISCOVER_FRAGMENT_TAG)
        }
    }

    private fun removeActiveFragment() {

        listOf(
            FAVORITES_FRAGMENT_TAG,
            DISCOVER_FRAGMENT_TAG
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

    private fun showFragment(fragment: Fragment, tag: String) {
        removeActiveFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, tag)
            .commit()
    }

    private fun setBottomNavigationBar() {
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.home -> showFragment(
                    DiscoverFragment(),
                    DISCOVER_FRAGMENT_TAG
                )
                R.id.tasks -> showFragment(
                    FavoritesFragment(),
                    FAVORITES_FRAGMENT_TAG
                )
            }

            true
        }
    }

    companion object {
        private const val FAVORITES_FRAGMENT_TAG = "FAVORITES_FRAGMENT_TAG"
        private const val DISCOVER_FRAGMENT_TAG = "DISCOVER_FRAGMENT_TAG"
    }

}
