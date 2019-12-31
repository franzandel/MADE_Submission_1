package com.example.madesubmission1.data.entities.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2019-12-29.
 * Android Engineer
 */

@Parcelize
open class BaseEntities(
    var name: String,
    var description: String,
    var release_date: String,
    var top_cast: String,
    var photo: Int
) : Parcelable