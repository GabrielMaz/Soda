package com.gabrielmaz.soda.data.repository

import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.repository.movies.MoviesDataStoreFactory

class MoviesSourceDataRepository(var factory: MoviesDataStoreFactory) : MoviesSourceRepository {

    override suspend fun getMovies(): List<Movie> {
        return factory.moviesDataStoreFactory.getMovies()
    }

}