package com.example.madesubmission1.data.network

import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.base.BaseAPIResponse
import com.example.madesubmission1.utils.AppConst.baseUrl
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Franz Andel on 2020-01-16.
 * Android Engineer
 */

class MovieNetwork {
    interface MoviesNetworkService {
        @GET("discover/movie")
        fun getAllMoviesFromAPI(@Query("api_key") apiKey: String): Call<BaseAPIResponse<MovieAPI>>
    }

    fun getMoviesNetworkService(): MoviesNetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MoviesNetworkService::class.java)
    }
}