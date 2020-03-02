package com.example.favoriteshower.external.helper

import android.database.Cursor
import com.example.favoriteshower.data.entities.api.MovieAPI
import com.example.favoriteshower.data.entities.api.TvShowAPI
import com.example.favoriteshower.data.entities.api.base.BaseAPI
import com.example.favoriteshower.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.MOVIE_TABLE_NAME

/**
 * Created by Franz Andel on 2020-01-29.
 * Android Engineer
 */

object MappingHelper {

    fun mapCursorToArrayList(apiCursor: Cursor, tableName: String): ArrayList<BaseAPI> {
        val listBaseAPI = ArrayList<BaseAPI>()
        apiCursor.moveToFirst()

        while (!apiCursor.isAfterLast) {
            val backdropPath =
                apiCursor.getString(apiCursor.getColumnIndexOrThrow("_backdrop_path"))
            val id = apiCursor.getInt(apiCursor.getColumnIndexOrThrow("__id"))
            val originalLanguage =
                apiCursor.getString(apiCursor.getColumnIndexOrThrow("_original_language"))
            val overview =
                apiCursor.getString(apiCursor.getColumnIndexOrThrow("__overview"))
            val popularity =
                apiCursor.getDouble(apiCursor.getColumnIndexOrThrow("__popularity"))
            val posterPath =
                apiCursor.getString(apiCursor.getColumnIndexOrThrow("_poster_path"))
            val voteAverage =
                apiCursor.getDouble(apiCursor.getColumnIndexOrThrow("_vote_average"))
            val voteCount =
                apiCursor.getInt(apiCursor.getColumnIndexOrThrow("_vote_count"))
            val isFavorite = apiCursor.getInt(apiCursor.columnCount - 1) > 0

            var adult: Boolean
            var originalTitle: String
            var releaseDate: String
            var title: String
            var video: Boolean

            var firstAirDate: String
            var name: String
            var originalName: String

            if (tableName == MOVIE_TABLE_NAME) {
                adult = apiCursor.getInt(0) > 0
                originalTitle =
                    apiCursor.getString(apiCursor.getColumnIndexOrThrow("originalTitle"))
                releaseDate =
                    apiCursor.getString(apiCursor.getColumnIndexOrThrow("releaseDate"))
                title = apiCursor.getString(apiCursor.getColumnIndexOrThrow("title"))
                video = apiCursor.getInt(4) > 0

                val movieAPI =
                    MovieAPI(
                        adult,
                        originalTitle,
                        releaseDate,
                        title,
                        video,
                        backdropPath,
                        arrayListOf(),
                        id,
                        originalLanguage,
                        overview,
                        popularity,
                        posterPath,
                        voteAverage,
                        voteCount,
                        isFavorite
                    )

                listBaseAPI.add(movieAPI)
            } else {
                firstAirDate =
                    apiCursor.getString(apiCursor.getColumnIndexOrThrow("firstAirDate"))
                name =
                    apiCursor.getString(apiCursor.getColumnIndexOrThrow("name"))
                originalName =
                    apiCursor.getString(apiCursor.getColumnIndexOrThrow("originalName"))

                val tvShowAPI =
                    TvShowAPI(
                        firstAirDate,
                        name,
                        arrayListOf(),
                        originalName,
                        backdropPath,
                        arrayListOf(),
                        id,
                        originalLanguage,
                        overview,
                        popularity,
                        posterPath,
                        voteAverage,
                        voteCount,
                        isFavorite
                    )

                listBaseAPI.add(tvShowAPI)
            }

            apiCursor.moveToNext()
        }

        return listBaseAPI
    }
}