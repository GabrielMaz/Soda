package com.gabrielmaz.soda.presentation.view.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Favorite
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.presentation.helpers.visibleIf
import com.gabrielmaz.soda.presentation.view.shared.MovieAdapter
import com.github.ybq.android.spinkit.style.Wave
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class FavoritesFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var adapter: MovieAdapter
    private val favoritesViewModel = FavoritesViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorites_loading.setIndeterminateDrawable(Wave())

        adapter = activity?.let {
            MovieAdapter(
                it
            ) { movie ->
                listener?.goToMovieDetails(movie)
            }
        }!!

        favorites_grid.adapter = adapter

        favoritesViewModel.favorites.observe(viewLifecycleOwner, Observer(this::loadMovies))
        favoritesViewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer(this::loadingStateChanged)
        )
        favoritesViewModel.isEmptyList.observe(viewLifecycleOwner, Observer(this::gridStateChanged))

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

    override fun onResume() {
        super.onResume()
        favoritesViewModel.loadFavorites(activity!!)
    }

    interface OnFragmentInteractionListener {
        fun goToMovieDetails(selectedMovie: Movie)
    }

    private fun loadMovies(favorites: List<Favorite>) {
        adapter.movies = favorites.map { f -> Movie(f.id, f.posterPath, f.overview, f.releaseDate, f.title, f.voteAverage, f.popularity) }
    }

    private fun loadingStateChanged(isLoading: Boolean) {
        favorites_loading.visibleIf(isLoading)
        favorites_content.visibleIf(!isLoading)
    }

    private fun gridStateChanged(isEmptyList: Boolean) {
        favorites_grid.visibleIf(!isEmptyList)
        favorites_empty.visibleIf(isEmptyList)
    }
}
