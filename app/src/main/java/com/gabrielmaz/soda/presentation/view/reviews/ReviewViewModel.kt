package com.gabrielmaz.soda.presentation.view.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielmaz.soda.data.controllers.ReviewController
import com.gabrielmaz.soda.data.models.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ReviewViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val reviewController = ReviewController()

    private val localReviews = MutableLiveData<ArrayList<Review>>()

    val reviews: LiveData<ArrayList<Review>>
        get() = localReviews

    fun loadReviews(movieId: Int) {
        launch(Dispatchers.IO) {
            try {
                val reviews = reviewController.getReviews(movieId).results
                localReviews.postValue(reviews)
            } catch (exception: Exception) {

            }
        }
    }
}