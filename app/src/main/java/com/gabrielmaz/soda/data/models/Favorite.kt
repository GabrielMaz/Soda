package com.gabrielmaz.soda.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Favorite(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo val overview: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo val title: String,
    @ColumnInfo val voteAverage: Float,
    @ColumnInfo val popularity: Float
) : Parcelable
