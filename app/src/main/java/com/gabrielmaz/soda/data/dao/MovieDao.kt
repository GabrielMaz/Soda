package com.gabrielmaz.soda.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gabrielmaz.soda.data.models.Movie
import androidx.room.OnConflictStrategy

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie where id = :id")
    suspend fun getMovie(id: Int): Movie?

    @Query("SELECT * FROM movie where isFavorite = 1")
    suspend fun getFavorites(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie): Long

    @Update
    suspend fun update(movie: Movie)
}