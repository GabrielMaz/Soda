package com.gabrielmaz.soda.data.controllers

import com.gabrielmaz.soda.data.service.ReviewService

class ReviewController {
    private val reviewService = RetrofitController.retrofit.create(ReviewService::class.java)

    suspend fun getReviews(movieId: Int) =
        reviewService.getReviews(movieId, RetrofitController.apiKey)
}