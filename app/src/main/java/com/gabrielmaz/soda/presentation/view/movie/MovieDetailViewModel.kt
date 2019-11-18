package com.gabrielmaz.soda.presentation.view.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.controllers.ReviewController
import com.gabrielmaz.soda.data.dao.FavoriteDao
import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.models.Favorite
import com.gabrielmaz.soda.data.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class MovieDetailViewModel(
    private val reviewController: ReviewController,
    private val favoriteDao: FavoriteDao
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val localIsFavorite = MutableLiveData<Boolean>()
    private val localReviews = MutableLiveData<Int>()

    val isFavorite: LiveData<Boolean>
        get() = localIsFavorite
    val reviews: LiveData<Int>
        get() = localReviews

    fun setMovie(movie: Movie) {
        launch(Dispatchers.IO) {
            val favorite = favoriteDao.getFavorite(movie.id)

            localIsFavorite.postValue(favorite != null)
        }
    }

    fun toggleFavorite(movie: Movie) {
        launch(Dispatchers.IO) {
            localIsFavorite.value?.let { oldValue ->
                val newValue = !oldValue
                val favorite = Favorite(
                    movie.id,
                    movie.posterPath,
                    movie.overview,
                    movie.releaseDate,
                    movie.title,
                    movie.voteAverage,
                    movie.popularity
                )

                if (newValue) {
                    favoriteDao.insert(favorite)
                } else {
                    favoriteDao.delete(favorite)
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