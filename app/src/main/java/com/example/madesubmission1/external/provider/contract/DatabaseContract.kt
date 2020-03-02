package com.example.madesubmission1.external.provider.contract

import android.provider.BaseColumns

/**
 * Created by Franz Andel on 2020-02-23.
 * Android Engineer
 */

object DatabaseContract {

    const val AUTHORITY = "com.franz.amazing.nicefilm"

    class EntertainmentColumns : BaseColumns {
        companion object {
            const val MOVIE_TABLE_NAME = "MovieAPI"
            const val TV_SHOW_TABLE_NAME = "TvShowAPI"
        }
    }
}