package com.gabrielmaz.soda.data.dao

import androidx.room.*
import com.gabrielmaz.soda.data.models.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite where id = :id")
    suspend fun getFavorite(id: Int): Favorite?

    @Query("SELECT * FROM favorite")
    suspend fun getFavorites(): List<Favorite>

    @Insert
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)
}