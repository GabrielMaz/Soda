package com.gabrielmaz.soda.views

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.controllers.DiscoverController
import com.gabrielmaz.soda.views.discover.DiscoverFragment
import com.gabrielmaz.soda.views.favorites.FavoritesFragment
import com.gabrielmaz.soda.views.movie.MovieDetailFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope,
    DiscoverFragment.OnFragmentInteractionListener,
    FavoritesFragment.OnFragmentInteractionListener,
    MovieDetailFragment.OnFragmentInteractionListener {
    override fun goToDiscovers() {
        showFragment(DiscoverFragment(), getString(R.string.discover_fragment_tag))
    }

    override fun goToMovieDetails() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MovieDetailFragment(), null)
            .addToBackStack(getString(R.string.movie_detail_fragment_tag))
            .commit()
//        showFragment(MovieDetailFragment(), getString(R.string.movie_detail_fragment_tag))
    }

    override fun onFragmentInteraction() {

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load(DiscoverFragmentTag)
    }

    private fun load(fragment: String) {

        setBottomNavigationBar()
        when (fragment) {
            DiscoverFragmentTag -> showFragment(DiscoverFragment(), DiscoverFragmentTag)
            FavoritesFragmentTag -> showFragment(FavoritesFragment(), FavoritesFragmentTag)

            else -> showFragment(DiscoverFragment(), DiscoverFragmentTag)
        }
    }

    private fun removeActiveFragment() {
        listOf(
            DiscoverFragmentTag,
            FavoritesFragmentTag
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
        supportFragmentManager.popBackStack()
        removeActiveFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, tag)
            .commit()
    }

    private fun setBottomNavigationBar() {
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            removeActiveFragment()

            when (menuItem.itemId) {
                R.id.home -> showFragment(DiscoverFragment(), DiscoverFragmentTag)
                R.id.tasks -> showFragment(FavoritesFragment(), FavoritesFragmentTag)
            }

            true
        }
    }

    companion object {
        const val DiscoverFragmentTag = "DiscoverFragment"
        const val FavoritesFragmentTag = "FavoritesFragment"
    }
}
