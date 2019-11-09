package com.gabrielmaz.soda.todo_lo_otro.sources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabrielmaz.soda.todo_lo_otro.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
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