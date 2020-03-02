package com.example.favoriteshower.external.provider.contract

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by Franz Andel on 2020-02-23.
 * Android Engineer
 */

object DatabaseContract {

    const val AUTHORITY = "com.franz.amazing.nicefilm"
    const val SCHEME = "content"

    class EntertainmentColumns : BaseColumns {
        companion object {
            const val MOVIE_TABLE_NAME = "MovieAPI"
            const val TV_SHOW_TABLE_NAME = "TvShowAPI"

            fun getContentURI(tableName: String): Uri {
                return Uri.Builder()
                    .scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(tableName)
                    .build()
            }
        }
    }
}