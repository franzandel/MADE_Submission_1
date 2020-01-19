package com.example.madesubmission1.data.entities.api

import com.example.madesubmission1.data.entities.api.base.BaseAPI
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieAPI(
    val adult: Boolean?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("backdrop_path")
    override val backdropPath: String?,
    @SerializedName("genre_ids")
    override val genreIds: List<Int>,
    @SerializedName("id")
    override val _id: Int?,
    @SerializedName("original_language")
    override val originalLanguage: String?,
    @SerializedName("overview")
    override val _overview: String?,
    @SerializedName("popularity")
    override val _popularity: Double?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("vote_average")
    override val voteAverage: Double?,
    @SerializedName("vote_count")
    override val voteCount: Int?
) : BaseAPI(
    backdropPath,
    genreIds,
    _id,
    originalLanguage,
    _overview,
    _popularity,
    posterPath,
    voteAverage,
    voteCount
)