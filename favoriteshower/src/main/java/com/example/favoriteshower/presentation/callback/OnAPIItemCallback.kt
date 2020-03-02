package com.example.favoriteshower.presentation.callback

import com.example.favoriteshower.data.entities.api.base.BaseAPI

/**
 * Created by Franz Andel on 2019-11-06.
 * Android Engineer
 */

interface OnAPIItemCallback {
    fun onItemClicked(baseAPI: BaseAPI)
}