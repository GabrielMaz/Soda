package com.gabrielmaz.soda.data.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "movie_genre_join",
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId")
    ), ForeignKey(entity = Genre::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("genreId"))]
)
data class MovieGenreJoin(
    val movieId: Int,
    val genreId: Int
)