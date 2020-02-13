package com.example.madesubmission1.data.entities.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.madesubmission1.data.entities.api.base.BaseAPI
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TvShowAPI(
    @SerializedName("first_air_date")
    var firstAirDate: String?,
    var name: String?,
    @SerializedName("origin_country")
    var originCountry: List<String>,
    @SerializedName("original_name")
    var originalName: String?,
    @ColumnInfo(name = "_backdrop_path")
    @SerializedName("backdrop_path")
    override var backdropPath: String?,
    @ColumnInfo(name = "_genre_ids")
    @SerializedName("genre_ids")
    override var genreIds: List<Int>,
    @PrimaryKey
    @ColumnInfo(name = "__id")
    @SerializedName("id")
    override var _id: Int?,
    @ColumnInfo(name = "_original_language")
    @SerializedName("original_language")
    override var originalLanguage: String?,
    @ColumnInfo(name = "__overview")
    @SerializedName("overview")
    override var _overview: String?,
    @ColumnInfo(name = "__popularity")
    @SerializedName("popularity")
    override var _popularity: Double?,
    @SerializedName("poster_path")
    @ColumnInfo(name = "_poster_path")
    override var posterPath: String?,
    @SerializedName("vote_average")
    @ColumnInfo(name = "_vote_average")
    override var voteAverage: Double?,
    @SerializedName("vote_count")
    @ColumnInfo(name = "_vote_count")
    override var voteCount: Int?,
    @SerializedName("is_favorite")
    @ColumnInfo(name = "_is_favorite")
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