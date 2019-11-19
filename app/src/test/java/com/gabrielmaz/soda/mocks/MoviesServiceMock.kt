package com.gabrielmaz.soda.mocks

import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.service.MovieService
import com.gabrielmaz.soda.data.service.response.DiscoverResponse

class MovieServiceMock(private val movies: List<Movie>) : MovieService {
    override suspend fun discoverMoviesByName(apiKey: String, query: String): DiscoverResponse {
        TODO("not implemented")
    }

    override suspend fun discoverMoviesByRate(
        apiKey: String,
        voteMin: Int,
        voteMax: Int
    ): DiscoverResponse {
        TODO("not implemented")
    }

    override suspend fun discoverMovies(apiKey: String): DiscoverResponse {
        return DiscoverResponse(
            0,
            2,
            1,
            ArrayList(movies)
        )
    }
}