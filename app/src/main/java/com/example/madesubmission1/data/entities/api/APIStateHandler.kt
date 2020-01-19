package com.example.madesubmission1.data.entities.api

import com.example.madesubmission1.data.entities.api.base.BaseAPIResponse

/**
 * Created by Franz Andel on 2020-01-19.
 * Android Engineer
 */

class APIStateHandler<T>(
    val baseAPIResponse: BaseAPIResponse<T>? = null,
    val error: Throwable? = null
)