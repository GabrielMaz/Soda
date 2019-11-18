package com.gabrielmaz.soda.data.repository.movies

import com.gabrielmaz.soda.data.controllers.MovieController
import com.gabrielmaz.soda.data.models.Movie

class CloudMoviesDataStore(private val movieController: MovieController) : MoviesDataStore {
    override suspend fun getMovies(): List<Movie> {
        return movieController.getMovies()
    }

}