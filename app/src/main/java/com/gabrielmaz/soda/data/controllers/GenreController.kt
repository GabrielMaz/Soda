package com.gabrielmaz.soda.data.controllers

import com.gabrielmaz.soda.data.service.GenresService

class GenreController(private val genresService: GenresService) {
    suspend fun getGenres() = genresService.getGenres(RetrofitController.apiKey).genres
}