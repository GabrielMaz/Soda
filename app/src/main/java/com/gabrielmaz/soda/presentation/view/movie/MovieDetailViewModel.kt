package com.gabrielmaz.soda.presentation.view.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.controllers.ReviewController
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.models.Review
import com.gabrielmaz.soda.data.sources.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class MovieDetailViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val reviewController = ReviewController()

    private val localIsFavorite = MutableLiveData<Boolean>()
    private val localReviews = MutableLiveData<Int>()

    val isFavorite: LiveData<Boolean>
        get() = localIsFavorite
    val reviews: LiveData<Int>
        get() = localReviews

    fun setMovie(context: Context, movie: Movie) {
        launch(Dispatchers.IO) {
            val movieDao = AppDatabase.getInstance(context).movieDao()
            val dbMovie = movieDao.getMovie(movie.id)

            localIsFavorite.postValue(dbMovie?.isFavorite ?: false)
        }
    }

    fun toggleFavorite(context: Context, movie: Movie) {
        launch(Dispatchers.IO) {
            localIsFavorite.value?.let { oldValue ->
                val newValue = !oldValue

                val movieDao = AppDatabase.getInstance(context).movieDao()

                val newMovie = Movie(
                    movie.id,
                    movie.posterPath,
                    movie.overview,
                    movie.releaseDate,
                    movie.title,
                    movie.voteAverage,
                    isFavorite = newValue
                )
                // upsert
                val id = movieDao.insert(newMovie)
                if (id == -1L) {
                    movieDao.update(newMovie)
                }
                localIsFavorite.postValue(newValue)
            }
        }
    }

    fun loadReviews(movieId: Int) {
        launch(Dispatchers.IO) {
            try {
                val reviews = reviewController.getReviews(movieId).results
                localReviews.postValue(reviews.size)
            } catch (exception: Exception) {

            }
        }
    }
}