package com.gabrielmaz.soda.mocks

import com.gabrielmaz.soda.data.controllers.MovieController
import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.helper.networking.NetworkingManager
import com.gabrielmaz.soda.data.repository.movies.MoviesDataStoreFactory

class MoviesDataStoreFactoryMock(
    controller: MovieController,
    dao: MovieDao,
    networkingManager: NetworkingManager
) : MoviesDataStoreFactory(controller, dao, networkingManager) {

}