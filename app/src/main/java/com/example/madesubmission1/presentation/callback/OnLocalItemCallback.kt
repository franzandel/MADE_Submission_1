package com.example.madesubmission1.presentation.callback

import com.example.madesubmission1.data.entities.local.base.BaseLocalModel

/**
 * Created by Franz Andel on 2020-01-19.
 * Android Engineer
 */

interface OnLocalItemCallback {
    fun onItemClicked(baseLocalModel: BaseLocalModel)
}