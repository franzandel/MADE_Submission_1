package com.example.madesubmission1.data.entities.api.base

/**
 * Created by Franz Andel on 2020-01-17.
 * Android Engineer
 */

data class BaseAPIResponse<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)