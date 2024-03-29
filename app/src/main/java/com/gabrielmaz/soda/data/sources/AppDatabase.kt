package com.gabrielmaz.soda.data.sources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabrielmaz.soda.data.dao.FavoriteDao
import com.gabrielmaz.soda.data.dao.GenreDao
import com.gabrielmaz.soda.data.dao.MovieDao
import com.gabrielmaz.soda.data.models.*

@Database(
    entities = [Movie::class, Favorite::class, Genre::class, MovieGenreJoin::class, FavoriteGenreJoin::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun genreDao(): GenreDao

    companion object {
        private val LOG_TAG = AppDatabase::class.java.canonicalName
        private val LOCK = Any()
        private val DATABASE_NAME = "MovieList"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder<AppDatabase>(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }
}