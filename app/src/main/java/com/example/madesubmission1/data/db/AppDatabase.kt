package com.example.madesubmission1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.madesubmission1.data.db.dao.api.MovieAPIDao
import com.example.madesubmission1.data.db.dao.api.TvShowAPIDao
import com.example.madesubmission1.data.entities.Converters
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI

/**
 * Created by Franz Andel on 2020-01-31.
 * Android Engineer
 */

@Database(
    entities = [TvShowAPI::class, MovieAPI::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "entertainment.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun tvShowAPIDao(): TvShowAPIDao
    abstract fun movieAPIDao(): MovieAPIDao
}