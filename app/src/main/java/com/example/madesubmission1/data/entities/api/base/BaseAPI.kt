package com.example.madesubmission1.data.entities.api.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2020-01-17.
 * Android Engineer
 */

@Parcelize
open class BaseAPI(
    open val backdropPath: String?,
    open val genreIds: List<Int>,
    open val _id: Int?,
    open val originalLanguage: String?,
    open val _overview: String?,
    open val _popularity: Double?,
    open val posterPath: String?,
    open val voteAverage: Double?,
    open val voteCount: Int?
) : Parcelable