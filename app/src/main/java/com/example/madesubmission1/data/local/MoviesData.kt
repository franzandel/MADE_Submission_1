package com.example.madesubmission1.data.local

import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.local.MovieLocal

/**
 * Created by Franz Andel on 2019-12-28.
 * Android Engineer
 */

class MoviesData(private val arrMovieName: Array<String>,
                 private val arrMovieTopCast: Array<String>,
                 private val arrMovieReleaseDate: Array<String>,
                 private val arrMovieDescription: Array<String>) {

    private val arrMovieImages = intArrayOf(
        R.drawable.poster_a_star,
        R.drawable.poster_aquaman,
        R.drawable.poster_avengerinfinity,
        R.drawable.poster_mortalengine,
        R.drawable.poster_bohemian,
        R.drawable.poster_bumblebee,
        R.drawable.poster_robinhood,
        R.drawable.poster_spiderman,
        R.drawable.poster_venom,
        R.drawable.poster_creed
    )

    val listMovie: ArrayList<MovieLocal>
        get() {
            val list = arrayListOf<MovieLocal>()
            for (position in arrMovieName.indices) {
                val movie =
                    MovieLocal(
                        arrMovieName[position],
                        arrMovieDescription[position],
                        arrMovieReleaseDate[position],
                        arrMovieTopCast[position],
                        arrMovieImages[position]
                    )

                list.add(movie)
            }
            return list
        }
}