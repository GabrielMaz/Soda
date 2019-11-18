package com.gabrielmaz.soda.data.repository.movies

import com.gabrielmaz.soda.data.controllers.MovieController
import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.helper.networking.NetworkingManager

open class MoviesDataStoreFactory(
    private var controller: MovieController,
    private var dao: MovieDao,
    private var networkingManager: NetworkingManager
) {

    open var moviesDataStoreFactory: MoviesDataStore
        get() {
            return createDataSourceFactory()
        }
        set(@Suppress("UNUSED_PARAMETER") value) {}

    private fun createDataSourceFactory() = if (networkingManager.isNetworkOnline()) {
        CloudMoviesDataStore(controller)
    } else {
        DatabaseMoviesDataStore(dao)
    }
}