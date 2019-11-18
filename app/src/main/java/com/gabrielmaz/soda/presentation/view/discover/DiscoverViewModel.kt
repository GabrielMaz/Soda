package com.gabrielmaz.soda.presentation.view.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.controllers.DiscoverController
import com.gabrielmaz.soda.data.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DiscoverViewModel(private val discoverController: DiscoverController) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

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
                val movies = discoverController.getDiscovers().results
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
                    val movies = discoverController.getDiscoversByName(name).results
                    localMovies.postValue(movies)
                    localIsEmptyList.postValue(movies.isEmpty())
                } catch (exception: Exception) {

                } finally {
                    localIsLoading.postValue(false)
                }
            }
        }
    }

    fun loadMoviesByRate(stars: Float) {
        var min: Int = 0
        var max: Int = 0

        if (stars.compareTo(0.0) == 0) {
            loadMovies()
            return
        } else if (stars.compareTo(1.0) <= 0) {
            min = 0
            max = 2
        } else if (stars.compareTo(2.0) <= 0) {
            min = 2
            max = 4
        } else if (stars.compareTo(3.0) <= 0) {
            min = 4
            max = 6
        } else if (stars.compareTo(4.0) <= 0) {
            min = 6
            max = 8
        } else if (stars.compareTo(5.0) <= 0) {
            min = 8
            max = 10
        }

        localIsLoading.postValue(true)
        localIsEmptyList.postValue(true)
        launch(Dispatchers.IO) {
            try {
                val movies = discoverController.getDiscoversByRate(min, max).results
                localMovies.postValue(movies)
                localIsEmptyList.postValue(movies.isEmpty())
            } catch (exception: Exception) {

            } finally {
                localIsLoading.postValue(false)
            }
        }
    }
}