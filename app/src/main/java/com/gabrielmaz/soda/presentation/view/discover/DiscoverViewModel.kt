package com.gabrielmaz.soda.presentation.view.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.controllers.DiscoverController
import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.helper.networking.NetworkingManager
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.repository.MoviesSourceRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DiscoverViewModel(
    private val discoverController: DiscoverController,
    private val moviesSourceRepository: MoviesSourceRepository,
    private val movieDao: MovieDao,
    private var networkingManager: NetworkingManager
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val localMovies = MutableLiveData<ArrayList<Movie>>()
    private val localIsLoading = MutableLiveData<Boolean>()
    private val localIsEmptyList = MutableLiveData<Boolean>()
    private val localErrorMessage = MutableLiveData<String>()

    val movies: LiveData<ArrayList<Movie>>
        get() = localMovies
    val isLoading: LiveData<Boolean>
        get() = localIsLoading
    val isEmptyList: LiveData<Boolean>
        get() = localIsEmptyList
    val errorMessage: LiveData<String>
        get() = localErrorMessage

    fun loadMovies() {
        searchJob?.cancel()
        searchJob = launch(Dispatchers.IO) {
            localIsLoading.postValue(true)
            localIsEmptyList.postValue(true)
            try {
                val movies = moviesSourceRepository.getMovies()

                if (networkingManager.isNetworkOnline()) {
                    movieDao.deleteAll()
                    movieDao.insertAll(movies)
                    // TODO enable search bar
                } else {
                    // TODO disable search bar
                }

                localMovies.postValue(ArrayList(movies))
                localIsEmptyList.postValue(movies.isEmpty())
                localIsLoading.postValue(false)
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    localIsLoading.postValue(false)
                    localErrorMessage.postValue(exception.message)
                }
                // TODO disable search bar in case of network error
            }
        }
    }

    private var searchJob: Job? = null

    fun loadMoviesByName(name: String) {
        if (name.trim() == "") {
            loadMovies()
        } else {
            searchJob?.cancel()
            searchJob = launch(Dispatchers.IO) {
                delay(500)
                localIsLoading.postValue(true)
                localIsEmptyList.postValue(true)
                try {
                    val movies = discoverController.getDiscoversByName(name).results
                    localMovies.postValue(movies)
                    localIsEmptyList.postValue(movies.isEmpty())
                    localIsLoading.postValue(false)
                } catch (exception: Exception) {
                    if (exception !is CancellationException) {
                        localIsLoading.postValue(false)
                        localErrorMessage.postValue(exception.message)
                    }
                    // TODO disable search bar in case of network error
                }
            }
        }
    }

    fun loadMoviesByRate(stars: Float) {
        searchJob?.cancel()
        searchJob = launch(Dispatchers.IO) {
            var min: Int = 0
            var max: Int = 0

            if (stars.compareTo(0.0) == 0) {
                loadMovies()
                return@launch
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
            try {
                val movies = discoverController.getDiscoversByRate(min, max).results
                localMovies.postValue(movies)
                localIsEmptyList.postValue(movies.isEmpty())
                localIsLoading.postValue(false)
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    localIsLoading.postValue(false)
                    localErrorMessage.postValue(exception.message)
                }
                // TODO disable search bar in case of network error
            }
        }
    }
}