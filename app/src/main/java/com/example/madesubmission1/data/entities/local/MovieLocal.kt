package com.example.madesubmission1.data.entities.local

import com.example.madesubmission1.data.entities.local.base.BaseLocalModel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2019-12-18.
 * Android Engineer
 */

@Parcelize
data class MovieLocal(
    override var name: String,
    override var description: String,
    override var release_date: String,
    override var top_cast: String,
    override var photo: Int
) : BaseLocalModel(name, description, release_date, top_cast, photo)