package com.gabrielmaz.soda.data.services

import com.gabrielmaz.soda.data.models.Discover
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {
    @GET("discover/movie")
    suspend fun getDiscovers(@Query("api_key") apiKey: String): Discover
}