package com.gabrielmaz.soda.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Review(
    @ForeignKey(entity = Movie::class, parentColumns = ["id"], childColumns = ["movieId"])
    @ColumnInfo val movieId: Int,
    @PrimaryKey val id: String,
    @ColumnInfo val author: String,
    @ColumnInfo val content: String
)