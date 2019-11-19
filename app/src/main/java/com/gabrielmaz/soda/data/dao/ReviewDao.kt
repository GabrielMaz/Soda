package com.gabrielmaz.soda.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gabrielmaz.soda.data.models.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review WHERE movieId = :movieId")
    suspend fun getReviewsByMovie(movieId: Int): List<Review>

    @Insert
    suspend fun insertAll(reviews: List<Review>)

    @Query("DELETE FROM review WHERE movieId = :movieId")
    suspend fun deleteMovieReviews(movieId: Int)
}