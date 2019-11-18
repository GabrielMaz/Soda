package com.gabrielmaz.soda.data.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "favorite_genre_join",
    primaryKeys = ["favoriteId", "genreId"],
    foreignKeys = [ForeignKey(
        entity = Favorite::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("favoriteId")
    ), ForeignKey(entity = Genre::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("genreId"))]
)
data class FavoriteGenreJoin(
    val favoriteId: Int,
    val genreId: Int
)