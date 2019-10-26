package com.gabrielmaz.soda.views.discover

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.adapters.DiscoverAdapter
import com.gabrielmaz.soda.controllers.DiscoverController
import com.gabrielmaz.soda.helpers.gone
import com.gabrielmaz.soda.helpers.visible
import com.gabrielmaz.soda.models.Movie
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DiscoverFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private var listener: OnFragmentInteractionListener? = null

    private var discoverController = DiscoverController()
    private lateinit var discovers: ArrayList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        load()
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
        fun goToMovieDetails()
    }

    private fun load() {
        discovers = ArrayList()

        launch(Dispatchers.IO) {
            try {
                discovers = discoverController.getDiscovers().results

                withContext(Dispatchers.Main) {
                    if (discovers.isNotEmpty()) {
                        discover_grid.visible()
                        discover_emptyview.gone()

                        discover_grid.adapter = activity?.let {
                            DiscoverAdapter(discovers, it) {
                                listener?.goToMovieDetails()
                            }
                        }
                    } else {
                        discover_grid.gone()
                        discover_emptyview.visible()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    println(ex.message)
                }
            }
        }

    }

}
