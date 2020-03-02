package com.example.madesubmission1.data.db.dao.api

import android.database.Cursor
import androidx.room.*
import com.example.madesubmission1.data.entities.api.MovieAPI

/**
 * Created by Franz Andel on 2020-01-30.
 * Android Engineer
 */

@Dao
interface MovieAPIDao {

    @Query("SELECT * FROM MovieAPI")
    fun getAllMovieAPI(): List<MovieAPI>

    @Query("SELECT * FROM MovieAPI WHERE _is_favorite = 1")
    fun getAllFavoriteMovieAPICursor(): Cursor

    @Query("SELECT * FROM MovieAPI WHERE __id=:id ")
    fun getMovieAPIById(id: Int): MovieAPI

    @Query("SELECT * FROM MovieAPI WHERE _is_favorite = 1")
    fun getAllFavoriteMovieAPI(): List<MovieAPI>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieAPI(listMovieAPI: List<MovieAPI>)

    @Update
    fun updateMovieAPI(movieAPI: MovieAPI)
}