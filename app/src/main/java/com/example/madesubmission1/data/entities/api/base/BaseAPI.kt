package com.example.madesubmission1.data.entities.api.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2020-01-17.
 * Android Engineer
 */

@Parcelize
open class BaseAPI(
    open var backdropPath: String?,
    open var genreIds: List<Int>,
    open var _id: Int?,
    open var originalLanguage: String?,
    open var _overview: String?,
    open var _popularity: Double?,
    open var posterPath: String?,
    open var voteAverage: Double?,
    open var voteCount: Int?,
    open var isFavorite: Boolean
) : Parcelable