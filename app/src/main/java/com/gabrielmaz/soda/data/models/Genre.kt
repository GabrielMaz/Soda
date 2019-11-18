package com.gabrielmaz.soda.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String
)