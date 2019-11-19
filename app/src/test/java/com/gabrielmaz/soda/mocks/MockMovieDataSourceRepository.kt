package com.gabrielmaz.soda.mocks

import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.repository.MoviesSourceRepository

class MockMovieDataSourceRepository : MoviesSourceRepository {
    override suspend fun getMovies(): List<Movie> {
        return listOf()
    }
}