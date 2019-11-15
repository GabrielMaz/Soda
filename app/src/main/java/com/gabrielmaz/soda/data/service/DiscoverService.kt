package com.gabrielmaz.soda.data.service

import com.gabrielmaz.soda.data.models.Discover
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {
    @GET("discover/movie")
    suspend fun getDiscovers(@Query("api_key") apiKey: String): Discover

    @GET("search/movie")
    suspend fun getDiscoversByName(@Query("api_key") apiKey: String, @Query("query") query: String): Discover

    @GET("discover/movie")
    suspend fun getDiscoversByRate(
        @Query("api_key") apiKey: String,
        @Query("vote_average.gte") voteMin: Int,
        @Query("vote_average.lte") voteMax: Int
    ): Discover
}