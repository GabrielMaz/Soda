package com.gabrielmaz.soda

import android.content.Context
import com.gabrielmaz.soda.mocks.MoviesDataStoreFactoryMock
import com.gabrielmaz.soda.mocks.NetworkingManagerMock
import com.gabrielmaz.soda.data.controllers.MovieController
import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.repository.MoviesSourceDataRepository
import com.gabrielmaz.soda.data.repository.movies.CloudMoviesDataStore
import com.gabrielmaz.soda.data.repository.movies.DatabaseMoviesDataStore
import com.gabrielmaz.soda.mocks.MovieServiceMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesDataTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Mock
    private lateinit var mockContext: Context

    private val testMovie = Movie(
        1,
        "poster",
        "overview",
        "releaseDate",
        "title",
        8f,
        100f
    )

    class MovieDaoMock : MovieDao {
        override suspend fun insertAll(movies: List<Movie>) {

        }

        override suspend fun deleteAll() {

        }

        override suspend fun getAll(): List<Movie> {
            return listOf()
        }

    }

    lateinit var moviesServiceMock: MovieServiceMock
    lateinit var movieDaoMock: MovieDaoMock
    lateinit var networkingManagerMock: NetworkingManagerMock
    lateinit var moviesDataStoreFactoryMock: MoviesDataStoreFactoryMock

    @Before
    fun beforeTest() {
        moviesServiceMock = MovieServiceMock(listOf(testMovie))
        movieDaoMock = MovieDaoMock()
        networkingManagerMock = NetworkingManagerMock(mockContext)
        moviesDataStoreFactoryMock =
            MoviesDataStoreFactoryMock(MovieController(moviesServiceMock), movieDaoMock, networkingManagerMock)
    }


    @Test
    fun testCloudSourceDataRetrieving() {
        networkingManagerMock.networkingAvailable = true

        assert(moviesDataStoreFactoryMock.moviesDataStoreFactory is CloudMoviesDataStore)
    }

    @Test
    fun testDatabaseSourceDataRetrieving() {
        networkingManagerMock.networkingAvailable = false

        assert(moviesDataStoreFactoryMock.moviesDataStoreFactory is DatabaseMoviesDataStore)
    }

    @Test
    fun testRepo() = runBlocking {
        networkingManagerMock.networkingAvailable = true

        val movies = MoviesSourceDataRepository(moviesDataStoreFactoryMock).getMovies()

        assert(movies[0].id == testMovie.id)
    }
}
