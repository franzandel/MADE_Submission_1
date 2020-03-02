package com.example.favoriteshower.data.entities.api

import com.example.favoriteshower.data.entities.api.base.BaseAPI
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowAPI(
    var firstAirDate: String?,
    var name: String?,
    var originCountry: List<String>,
    var originalName: String?,
    override var backdropPath: String?,
    override var genreIds: List<Int>,
    override var _id: Int?,
    override var originalLanguage: String?,
    override var _overview: String?,
    override var _popularity: Double?,
    override var posterPath: String?,
    override var voteAverage: Double?,
    override var voteCount: Int?,
    override var isFavorite: Boolean
) : BaseAPI(
    backdropPath,
    genreIds,
    _id,
    originalLanguage,
    _overview,
    _popularity,
    posterPath,
    voteAverage,
    voteCount,
    isFavorite
)