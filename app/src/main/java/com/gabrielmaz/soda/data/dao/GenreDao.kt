package com.gabrielmaz.soda.data.dao

import androidx.room.*
import com.gabrielmaz.soda.data.models.Genre


@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    suspend fun getGenres(): List<Genre>

    @Insert
    suspend fun insertAll(genres: List<Genre>)

    @Query("SELECT * FROM genre WHERE id = :id")
    suspend fun getGenre(id: Int): Genre

    @Query("DELETE FROM genre")
    suspend fun deleteAll()
}