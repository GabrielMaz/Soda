package com.gabrielmaz.soda.data.dao

import androidx.room.*
import com.gabrielmaz.soda.data.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    @Insert
    suspend fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}