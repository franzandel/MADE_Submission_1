package com.example.madesubmission1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.madesubmission1.data.AppRepository
import com.example.madesubmission1.data.entities.api.APIStateHandler
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI


/**
 * Created by Franz Andel on 2020-01-17.
 * Android Engineer
 */

class ListFragmentViewModel : ViewModel() {
    private val appRepository = AppRepository()
    private val medldMovie = MediatorLiveData<APIStateHandler<MovieAPI>>()
    private val medldTvShow = MediatorLiveData<APIStateHandler<TvShowAPI>>()

    fun getListMovie(): LiveData<APIStateHandler<MovieAPI>> {
        medldMovie.addSource(appRepository.getAllMovie()) {
            medldMovie.value = it
        }

        return medldMovie
    }

    fun getListTvShow(): LiveData<APIStateHandler<TvShowAPI>> {
        medldTvShow.addSource(appRepository.getAllTvShow()) {
            medldTvShow.value = it
        }

        return medldTvShow
    }
}