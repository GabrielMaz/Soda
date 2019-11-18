package com.gabrielmaz.soda.data.repository.movies

import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.models.Movie

class DatabaseMoviesDataStore(private val movieDao: MovieDao) : MoviesDataStore {

    override suspend fun getMovies(): List<Movie> {
        return movieDao.getAll()
    }
}