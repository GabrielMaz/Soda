package com.gabrielmaz.soda.todo_lo_otro.services

import com.gabrielmaz.soda.todo_lo_otro.models.Discover
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {
    @GET("discover/movie")
    suspend fun getDiscovers(@Query("api_key") apiKey: String): Discover
}