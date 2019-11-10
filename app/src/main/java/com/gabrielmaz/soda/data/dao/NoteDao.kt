package com.gabrielmaz.soda.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.gabrielmaz.soda.data.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM movie where isFavorite = 1")
    suspend fun getFavorites(): List<Movie>
}