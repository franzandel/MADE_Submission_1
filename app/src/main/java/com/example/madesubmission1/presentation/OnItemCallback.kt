package com.example.madesubmission1.presentation

import com.example.madesubmission1.data.entities.base.BaseEntities

/**
 * Created by Franz Andel on 2019-11-06.
 * Android Engineer
 */

interface OnItemCallback {
    fun onItemClicked(baseEntities: BaseEntities)
}