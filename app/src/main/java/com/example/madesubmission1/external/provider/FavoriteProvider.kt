package com.example.madesubmission1.external.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.madesubmission1.data.db.AppDatabase
import com.example.madesubmission1.external.provider.contract.DatabaseContract.AUTHORITY
import com.example.madesubmission1.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.MOVIE_TABLE_NAME
import com.example.madesubmission1.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.TV_SHOW_TABLE_NAME

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val TV_SHOW = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var appDatabase: AppDatabase

        init {
            // content://com.franz.amazing.nicefilm/MovieAPI
            uriMatcher.addURI(AUTHORITY, MOVIE_TABLE_NAME, MOVIE)
            // content://com.franz.amazing.nicefilm/TvShowAPI
            uriMatcher.addURI(AUTHORITY, TV_SHOW_TABLE_NAME, TV_SHOW)
        }
    }

    override fun onCreate(): Boolean {
        val context = context as Context
        appDatabase = AppDatabase.invoke(context.applicationContext)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        return when (uriMatcher.match(uri)) {
            MOVIE -> appDatabase.movieAPIDao().getAllFavoriteMovieAPICursor()
            TV_SHOW -> appDatabase.tvShowAPIDao().getAllFavoriteTvShowAPICursor()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {

        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}
