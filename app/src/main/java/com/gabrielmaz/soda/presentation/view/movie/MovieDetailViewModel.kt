package com.gabrielmaz.soda.presentation.view.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.controllers.ReviewController
import com.gabrielmaz.soda.data.dao.FavoriteDao
import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.dao.ReviewDao
import com.gabrielmaz.soda.data.models.Favorite
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.models.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class MovieDetailViewModel(
    private val reviewController: ReviewController,
    private val favoriteDao: FavoriteDao,
    private val reviewDao: ReviewDao
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val localIsFavorite = MutableLiveData<Boolean>()
    private val localReviews = MutableLiveData<ArrayList<Review>>()

    val isFavorite: LiveData<Boolean>
        get() = localIsFavorite
    val reviews: LiveData<ArrayList<Review>>
        get() = localReviews

    fun setMovie(movie: Movie) {
        loadReviews(movie.id)
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
                    reviewDao.insertAll(ArrayList(localReviews.value!!))
                } else {
                    favoriteDao.delete(favorite)
                    reviewDao.deleteMovieReviews(movie.id)
                }
                localIsFavorite.postValue(newValue)
            }
        }
    }

    private fun loadReviews(movieId: Int) {
        launch(Dispatchers.IO) {
            try {
                val reviewResponses = reviewController.getReviews(movieId).results
                val reviews = reviewResponses.map {
                    Review(
                        movieId,
                        it.id,
                        it.author,
                        it.content
                    )
                }
                localReviews.postValue(ArrayList(reviews))
            } catch (exception: Exception) {

            }
        }
    }
}