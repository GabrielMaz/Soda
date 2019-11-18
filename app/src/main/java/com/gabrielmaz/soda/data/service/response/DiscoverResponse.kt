package com.gabrielmaz.soda.data.service.response

import com.gabrielmaz.soda.data.models.Movie

data class DiscoverResponse(
    val page: Int,
    val totalResult : Int,
    val totalPages: Int,
    val results: ArrayList<Movie>
)
