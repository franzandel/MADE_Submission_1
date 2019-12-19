package com.example.madesubmission1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2019-12-18.
 * Android Engineer
 */

@Parcelize
data class Movie(
    var name: String,
    var description: String,
    var release_date: String,
    var top_cast: String,
    var photo: Int
) : Parcelable