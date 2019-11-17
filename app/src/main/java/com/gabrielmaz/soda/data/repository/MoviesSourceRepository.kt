package com.gabrielmaz.soda.data.repository

import com.gabrielmaz.soda.data.models.Movie

interface MoviesSourceRepository {

    suspend fun getMovies(): List<Movie>
}