package com.example.madesubmission1.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.madesubmission1.data.AppRepository
import com.example.madesubmission1.data.db.AppDatabase
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Franz Andel on 2020-02-05.
 * Android Engineer
 */

class ListDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase(application))

    fun updateMovieAPIIsFavoriteInDB(movieAPI: MovieAPI) {
        GlobalScope.launch(Dispatchers.IO) {
            appRepository.updateMovieAPIIsFavoriteInDB(movieAPI)
        }
    }

    fun updateTvShowAPIIsFavoriteInDB(tvShowAPI: TvShowAPI) {
        GlobalScope.launch(Dispatchers.IO) {
            appRepository.updateTvShowAPIIsFavoriteInDB(tvShowAPI)
        }
    }

    fun isMovieAPIFavoriteInDB(movieAPI: MovieAPI, callback: (Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            callback(getMovieAPIByIdFromDBAsync(movieAPI._id!!))
        }
    }

    private suspend fun getMovieAPIByIdFromDBAsync(id: Int) =
        withContext(Dispatchers.IO) {
            val movieById = appRepository.getMovieAPIByIdFromDB(id)
            movieById?.isFavorite ?: false
        }

    fun isTvShowAPIFavoriteInDB(tvShowAPI: TvShowAPI, callback: (Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            callback(getTvShowAPIByIdFromDBAsync(tvShowAPI._id!!))
        }
    }

    private suspend fun getTvShowAPIByIdFromDBAsync(id: Int) =
        withContext(Dispatchers.IO) {
            val tvShowById = appRepository.getTvShowAPIByIdFromDB(id)
            tvShowById?.isFavorite ?: false
        }
}