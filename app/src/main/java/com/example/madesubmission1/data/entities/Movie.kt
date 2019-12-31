package com.example.madesubmission1.data.entities

import com.example.madesubmission1.data.entities.base.BaseEntities
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2019-12-18.
 * Android Engineer
 */

@Parcelize
data class Movie(
    var movieName: String,
    var movieDescription: String,
    var movieReleaseDate: String,
    var movieTopCast: String,
    var moviePhoto: Int
) : BaseEntities(movieName, movieDescription, movieReleaseDate, movieTopCast, moviePhoto)