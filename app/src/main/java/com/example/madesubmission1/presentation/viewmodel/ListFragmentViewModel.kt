package com.example.madesubmission1.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.madesubmission1.data.AppRepository
import com.example.madesubmission1.data.db.AppDatabase
import com.example.madesubmission1.data.entities.api.APIStateHandler
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by Franz Andel on 2020-01-17.
 * Android Engineer
 */

class ListFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val appRepository = AppRepository(AppDatabase(application))
    private val medldMovie = MediatorLiveData<APIStateHandler<MovieAPI>>()
    private val medldTvShow = MediatorLiveData<APIStateHandler<TvShowAPI>>()

    fun getListMovieFromAPI(): LiveData<APIStateHandler<MovieAPI>> {
        medldMovie.addSource(appRepository.getAllMovieFromAPI()) {
            medldMovie.value = it
        }

        return medldMovie
    }

    fun getListTvShowFromAPI(): LiveData<APIStateHandler<TvShowAPI>> {
        medldTvShow.addSource(appRepository.getAllTvShowFromAPI()) {
            medldTvShow.value = it
        }

        return medldTvShow
    }

    fun insertAllMovieAPIIntoDB(listMovieAPI: List<MovieAPI>) {
        GlobalScope.launch(Dispatchers.IO) {
            appRepository.insertAllMovieAPIIntoDB(listMovieAPI)
        }
    }

    fun insertAllTvShowAPIIntoDB(listTvShowAPI: List<TvShowAPI>) {
        GlobalScope.launch(Dispatchers.IO) {
            appRepository.insertAllTvShowAPIIntoDB(listTvShowAPI)
        }
    }

    fun getAllMovieAPIFromDB(callback: (List<MovieAPI>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            callback(getAllMovieAPIFromDBAsync())
        }
    }

    private suspend fun getAllMovieAPIFromDBAsync() =
        withContext(Dispatchers.IO) {
            appRepository.getAllMovieAPIFromDB()
        }

    fun getAllFavoriteMovieAPIFromDB(callback: (List<MovieAPI>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            callback(getAllFavoriteMovieAPIFromDBAsync())
        }
    }

    private suspend fun getAllFavoriteMovieAPIFromDBAsync() =
        withContext(Dispatchers.IO) {
            appRepository.getAllFavoriteMovieAPIFromDB()
        }

    fun getAllTvShowAPIFromDB(callback: (List<TvShowAPI>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            callback(getTvShowAPIFromDBAsync())
        }
    }

    private suspend fun getTvShowAPIFromDBAsync() =
        withContext(Dispatchers.IO) {
            appRepository.getAllTvShowAPIFromDB()
        }

    fun getAllFavoriteTvShowAPIFromDB(callback: (List<TvShowAPI>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            callback(getAllFavoriteTvShowAPIFromDBAsync())
        }
    }

    private suspend fun getAllFavoriteTvShowAPIFromDBAsync() =
        withContext(Dispatchers.IO) {
            appRepository.getAllFavoriteTvShowAPIFromDB()
        }
}