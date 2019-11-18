package com.gabrielmaz.soda

import android.content.Context
import com.gabrielmaz.soda.data.models.Movie
import com.gabrielmaz.soda.data.service.MovieService
import com.gabrielmaz.soda.data.service.response.DiscoverResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesDataTest {

    @Mock
    private lateinit var mockContext: Context

//    class MovieServiceMock : MovieService {
//        override suspend fun discoverMovies(apiKey: String): DiscoverResponse {
//            return DiscoverResponse(
//                0,
//                2,
//                1,
//                arrayListOf(
//                    Movie(1, "poster", "overview", "releaseDate", "title", 8f, 100f)
//                )
//            )
//        }
//    }
//
//    lateinit var moviesServiceMock: MovieServiceMock
//    lateinit var noteDaoMock: NoteDaoMock
//    lateinit var networkingManagerMock: NetworkingManagerMock
//    lateinit var notesDataStoreFactoryMock: NotesDataStoreFactoryMock

    @Before
    fun beforeTest() {
//        noteServiceMock = NotesServiceMock()
//        noteDaoMock = NoteDaoMock()
//        networkingManagerMock = NetworkingManagerMock(mockContext)
//        notesDataStoreFactoryMock =
//            NotesDataStoreFactoryMock(noteServiceMock, noteDaoMock, networkingManagerMock)
    }

    @After
    fun afterTest() {

    }


//    @Test
//    fun testCloudSourceDataRetrieving() {
//        networkingManagerMock.networkingAvailable = true
//
//        assert(notesDataStoreFactoryMock.notesDataStoreFactory is CloudNotesDataStore)
//    }
//
//    @Test
//    fun testDatabaseSourceDataRetrieving() {
//        networkingManagerMock.networkingAvailable = false
//
//        assert(notesDataStoreFactoryMock.notesDataStoreFactory is DatabaseNotesDataStore)
//    }
//
//    @Test
//    fun testNetworking() {
//        val networkingManager = NetworkingManager(mockContext)
//        assert(networkingManager.isNetworkOnline())
//    }

    @Test
    fun testSourceData() {

    }

}
