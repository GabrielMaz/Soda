package com.gabrielmaz.soda.presentation.view.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.controllers.DiscoverController
import com.gabrielmaz.soda.data.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DiscoverViewModel() : ViewModel(),
    CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val controller = DiscoverController()
    private val localMovies = MutableLiveData<ArrayList<Movie>>()
    private val localIsLoading = MutableLiveData<Boolean>()
    private val localIsEmptyList = MutableLiveData<Boolean>()

    val movies: LiveData<ArrayList<Movie>>
        get() = localMovies
    val isLoading: LiveData<Boolean>
        get() = localIsLoading
    val isEmptyList: LiveData<Boolean>
        get() = localIsEmptyList

    fun loadMovies() {
        localIsLoading.postValue(true)
        localIsEmptyList.postValue(true)
        launch(Dispatchers.IO) {
            try {
                val movies = controller.getDiscovers().results
                localMovies.postValue(movies)
                localIsEmptyList.postValue(movies.isEmpty())
            } catch (exception: Exception) {

            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    fun loadMoviesByName(name: String) {

        if (name.trim() == "") {
            loadMovies()
        } else {

            localIsLoading.postValue(true)
            localIsEmptyList.postValue(true)
            launch(Dispatchers.IO) {
                try {
                    val movies = controller.getDiscoversByName(name).results
                    localMovies.postValue(movies)
                    localIsEmptyList.postValue(movies.isEmpty())
                } catch (exception: Exception) {

                } finally {
                    localIsLoading.postValue(false)
                }
            }
        }
    }
}