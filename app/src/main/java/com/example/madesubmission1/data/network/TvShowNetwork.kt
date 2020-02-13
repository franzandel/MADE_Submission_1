package com.example.madesubmission1.data.network

import com.example.madesubmission1.data.entities.api.TvShowAPI
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

class TvShowNetwork {
    interface TvShowNetworkService {
        @GET("discover/tv")
        fun getAllTvShowFromAPI(@Query("api_key") apiKey: String): Call<BaseAPIResponse<TvShowAPI>>
    }

    fun getTvShowNetworkService(): TvShowNetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TvShowNetworkService::class.java)
    }
}