package com.gabrielmaz.soda.presentation.view.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.presentation.helpers.visibleIf
import com.github.ybq.android.spinkit.style.Wave
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DiscoverFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private var listener: OnFragmentInteractionListener? = null
    
    private lateinit var adapter: DiscoverAdapter
    private val discoverViewModel = DiscoverViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discovers_loading.setIndeterminateDrawable(Wave())

        adapter = activity?.let {
            DiscoverAdapter(
                it
            ) { movie ->
                listener?.goToMovieDetails(movie)
            }
        }!!
        discover_grid.adapter = adapter

        discover_search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                discoverViewModel.loadMoviesByName(newText)
                return true
            }
        })

        discoverViewModel.loadMovies()
        discoverViewModel.movies.observe(viewLifecycleOwner, Observer(this::loadMovies))
        discoverViewModel.isLoading.observe(viewLifecycleOwner, Observer(this::loadingStateChanged))
        discoverViewModel.isEmptyList.observe(viewLifecycleOwner, Observer(this::gridStateChanged))
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

    interface OnFragmentInteractionListener {
        fun goToMovieDetails(selectedMovie: Movie)
    }

    private fun loadMovies(discovers: ArrayList<Movie>) {
        adapter.discovers = discovers
    }

    private fun loadingStateChanged(isLoading: Boolean) {
        discovers_loading.visibleIf(isLoading)
        discover_content.visibleIf(!isLoading)
    }

    private fun gridStateChanged(isEmptyList: Boolean) {
        discover_grid.visibleIf(!isEmptyList)
        discover_emptyview.visibleIf(isEmptyList)
    }
}
