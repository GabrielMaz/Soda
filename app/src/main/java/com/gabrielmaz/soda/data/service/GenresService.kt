package com.gabrielmaz.soda.data.service

import com.gabrielmaz.soda.data.service.response.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {
    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): GenreResponse
}