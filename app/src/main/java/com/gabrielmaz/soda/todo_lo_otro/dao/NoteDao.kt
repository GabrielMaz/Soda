package com.gabrielmaz.soda.todo_lo_otro.dao

import androidx.room.Dao
import androidx.room.Query
import com.gabrielmaz.soda.todo_lo_otro.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM movie where isFavorite = 1")
    suspend fun getFavorites(): List<Movie>
}