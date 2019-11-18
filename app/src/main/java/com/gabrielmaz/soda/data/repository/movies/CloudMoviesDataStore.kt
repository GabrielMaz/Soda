package com.gabrielmaz.soda.data.repository.movies

import com.gabrielmaz.soda.data.controllers.DiscoverController
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.service.DiscoverService

class CloudMoviesDataStore(private val discoverController: DiscoverController) : MoviesDataStore {
    override suspend fun getMovies(): List<Movie> {
        return discoverController.getDiscovers().results
    }

}