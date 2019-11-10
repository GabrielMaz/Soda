package com.gabrielmaz.soda.data.models

data class Discover(
    val page: Int,
    val totalResult : Int,
    val totalPages: Int,
    val results: ArrayList<Movie>
)
