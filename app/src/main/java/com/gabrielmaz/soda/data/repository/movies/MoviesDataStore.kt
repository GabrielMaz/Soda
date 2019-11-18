package com.gabrielmaz.soda.data.repository.movies

import com.gabrielmaz.soda.data.models.Movie

interface MoviesDataStore {

    suspend fun getMovies(): List<Movie>
}