package com.example.madesubmission1.data.entities

import com.example.madesubmission1.data.entities.base.BaseEntities
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 2019-12-28.
 * Android Engineer
 */

@Parcelize
data class TvShow(
    var tvShowName: String,
    var tvShowDescription: String,
    var tvShowReleaseDate: String,
    var tvShowTopCast: String,
    var tvShowPhoto: Int
) : BaseEntities(tvShowName, tvShowDescription, tvShowReleaseDate, tvShowTopCast, tvShowPhoto)