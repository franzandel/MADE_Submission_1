package com.example.madesubmission1.data.entities.local.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2019-12-29.
 * Android Engineer
 */

@Parcelize
open class BaseLocalModel(
    open var name: String,
    open var description: String,
    open var release_date: String,
    open var top_cast: String,
    open var photo: Int
) : Parcelable