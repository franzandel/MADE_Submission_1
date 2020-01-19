package com.example.madesubmission1.data.local

import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.local.TvShowLocal

/**
 * Created by Franz Andel on 2019-12-28.
 * Android Engineer
 */

class TvShowData(private val arrTvShowName: Array<String>,
                 private val arrTvShowTopCast: Array<String>,
                 private val arrTvShowReleaseDate: Array<String>,
                 private val arrTvShowDescription: Array<String>) {

    private val arrTvShowImages = intArrayOf(
        R.drawable.doom_patrol,
        R.drawable.hanna,
        R.drawable.fast_furious_spy_racers,
        R.drawable.the_mandalorian,
        R.drawable.his_dark_materials,
        R.drawable.batwoman,
        R.drawable.mrs_fletcher,
        R.drawable.carnival_row,
        R.drawable.traces,
        R.drawable.dororo
    )

    val listTvShow: ArrayList<TvShowLocal>
        get() {
            val list = arrayListOf<TvShowLocal>()
            for (position in arrTvShowName.indices) {
                val tvShow =
                    TvShowLocal(
                        arrTvShowName[position],
                        arrTvShowDescription[position],
                        arrTvShowReleaseDate[position],
                        arrTvShowTopCast[position],
                        arrTvShowImages[position]
                    )

                list.add(tvShow)
            }
            return list
        }
}