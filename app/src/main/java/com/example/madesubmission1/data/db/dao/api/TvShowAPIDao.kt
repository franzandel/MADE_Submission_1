package com.example.madesubmission1.data.db.dao.api

import androidx.room.*
import com.example.madesubmission1.data.entities.api.TvShowAPI

/**
 * Created by Franz Andel on 2020-01-30.
 * Android Engineer
 */

@Dao
interface TvShowAPIDao {

    @Query("SELECT * FROM TvShowAPI")
    fun getAllTvShowAPI(): List<TvShowAPI>

    @Query("SELECT * FROM TvShowAPI WHERE __id=:id ")
    fun getTvShowAPIById(id: Int): TvShowAPI

    @Query("SELECT * FROM TvShowAPI WHERE _is_favorite = 1")
    fun getAllFavoriteTvShowAPI(): List<TvShowAPI>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTvShowAPI(listTvShowAPI: List<TvShowAPI>)

    @Update
    fun updateTvShowAPI(tvShowAPI: TvShowAPI)
}