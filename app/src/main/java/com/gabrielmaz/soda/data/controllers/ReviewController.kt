package com.gabrielmaz.soda.data.controllers

import com.gabrielmaz.soda.data.service.ReviewService

class ReviewController(private val reviewService: ReviewService) {
    suspend fun getReviews(movieId: Int) =
        reviewService.getReviews(movieId, RetrofitController.apiKey)
}