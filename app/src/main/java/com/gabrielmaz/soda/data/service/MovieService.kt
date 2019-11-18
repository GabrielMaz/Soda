package com.gabrielmaz.soda.data.service

import com.gabrielmaz.soda.data.service.response.DiscoverResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun discoverMovies(@Query("api_key") apiKey: String): DiscoverResponse

    @GET("search/movie")
    suspend fun discoverMoviesByName(@Query("api_key") apiKey: String, @Query("query") query: String): DiscoverResponse

    @GET("discover/movie")
    suspend fun discoverMoviesByRate(
        @Query("api_key") apiKey: String,
        @Query("vote_average.gte") voteMin: Int,
        @Query("vote_average.lte") voteMax: Int
    ): DiscoverResponse
}