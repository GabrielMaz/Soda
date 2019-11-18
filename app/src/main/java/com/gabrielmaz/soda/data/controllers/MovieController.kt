package com.gabrielmaz.soda.data.controllers

import com.gabrielmaz.soda.data.service.MovieService

class MovieController(private val movieService: MovieService) {
    suspend fun getMovies() = movieService.discoverMovies(RetrofitController.apiKey).results

    suspend fun getMoviesByName(name: String) = movieService.discoverMoviesByName(RetrofitController.apiKey, name).results

    suspend fun getMoviesByRate(min: Int, max: Int) = movieService.discoverMoviesByRate(RetrofitController.apiKey, min, max).results
}