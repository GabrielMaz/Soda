package com.gabrielmaz.soda.mocks

import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.models.Movie

class MovieDaoMock : MovieDao {

    override suspend fun getAll(): List<Movie> {
        return listOf()
    }

    override suspend fun insertAll(movies: List<Movie>) {

    }

    override suspend fun deleteAll() {

    }

}