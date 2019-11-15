package com.gabrielmaz.soda.data.service

import com.gabrielmaz.soda.data.service.response.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {
    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(@Path("movie_id") movieId: Int ,@Query("api_key") apiKey: String): ReviewResponse
}