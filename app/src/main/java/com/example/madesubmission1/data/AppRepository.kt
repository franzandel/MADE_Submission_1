package com.example.madesubmission1.data

import androidx.lifecycle.MutableLiveData
import com.example.madesubmission1.data.db.AppDatabase
import com.example.madesubmission1.data.entities.api.APIStateHandler
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI
import com.example.madesubmission1.data.entities.api.base.BaseAPIResponse
import com.example.madesubmission1.data.network.MovieNetwork
import com.example.madesubmission1.data.network.TvShowNetwork
import com.example.madesubmission1.utils.AppConst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Franz Andel on 2020-01-16.
 * Android Engineer
 */

class AppRepository(private val appDatabase: AppDatabase) {
    private val movieNetworkService = MovieNetwork().getMoviesNetworkService()
    private val tvShowNetworkService = TvShowNetwork().getTvShowNetworkService()

    fun getAllMovieFromAPI(): MutableLiveData<APIStateHandler<MovieAPI>> {
        val mutldAPIStateHandler = MutableLiveData<APIStateHandler<MovieAPI>>()

        movieNetworkService.getAllMoviesFromAPI(AppConst.apiKey)
            .enqueue(object : Callback<BaseAPIResponse<MovieAPI>> {
                override fun onResponse(
                    call: Call<BaseAPIResponse<MovieAPI>>,
                    response: Response<BaseAPIResponse<MovieAPI>>
                ) {
                    if (response.isSuccessful) {
                        mutldAPIStateHandler.value =
                            APIStateHandler(
                                baseAPIResponse = response.body()
                            )
                    }
                }

                override fun onFailure(call: Call<BaseAPIResponse<MovieAPI>>, t: Throwable) {
                    mutldAPIStateHandler.value =
                        APIStateHandler(
                            error = t
                        )
                }
            })

        return mutldAPIStateHandler
    }

    fun getAllTvShowFromAPI(): MutableLiveData<APIStateHandler<TvShowAPI>> {
        val mutldAPIStateHandler = MutableLiveData<APIStateHandler<TvShowAPI>>()

        tvShowNetworkService.getAllTvShowFromAPI(AppConst.apiKey)
            .enqueue(object : Callback<BaseAPIResponse<TvShowAPI>> {
                override fun onResponse(
                    call: Call<BaseAPIResponse<TvShowAPI>>,
                    response: Response<BaseAPIResponse<TvShowAPI>>
                ) {
                    if (response.isSuccessful) {
                        mutldAPIStateHandler.value =
                            APIStateHandler(
                                baseAPIResponse = response.body()
                            )
                    }
                }

                override fun onFailure(call: Call<BaseAPIResponse<TvShowAPI>>, t: Throwable) {
                    mutldAPIStateHandler.value =
                        APIStateHandler(
                            error = t
                        )
                }
            })

        return mutldAPIStateHandler
    }

    fun getMovieAPIByIdFromDB(id: Int): MovieAPI? {
        return appDatabase.movieAPIDao().getMovieAPIById(id)
    }

    fun getTvShowAPIByIdFromDB(id: Int): TvShowAPI? {
        return appDatabase.tvShowAPIDao().getTvShowAPIById(id)
    }

    fun getAllMovieAPIFromDB(): List<MovieAPI> {
        return appDatabase.movieAPIDao().getAllMovieAPI()
    }

    fun getAllTvShowAPIFromDB(): List<TvShowAPI> {
        return appDatabase.tvShowAPIDao().getAllTvShowAPI()
    }

    fun getAllFavoriteMovieAPIFromDB(): List<MovieAPI> {
        return appDatabase.movieAPIDao().getAllFavoriteMovieAPI()
    }

    fun getAllFavoriteTvShowAPIFromDB(): List<TvShowAPI> {
        return appDatabase.tvShowAPIDao().getAllFavoriteTvShowAPI()
    }

    fun insertAllMovieAPIIntoDB(listMovieAPI: List<MovieAPI>) {
        appDatabase.movieAPIDao().insertAllMovieAPI(listMovieAPI)
    }

    fun insertAllTvShowAPIIntoDB(listTvShowAPI: List<TvShowAPI>) {
        appDatabase.tvShowAPIDao().insertAllTvShowAPI(listTvShowAPI)
    }

    fun updateMovieAPIIsFavoriteInDB(movieAPI: MovieAPI) {
        appDatabase.movieAPIDao().updateMovieAPI(movieAPI)
    }

    fun updateTvShowAPIIsFavoriteInDB(tvShowAPI: TvShowAPI) {
        appDatabase.tvShowAPIDao().updateTvShowAPI(tvShowAPI)
    }
}