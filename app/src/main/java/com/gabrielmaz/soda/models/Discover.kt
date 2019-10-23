package com.gabrielmaz.soda.models

data class Discover(
    val page: Int,
    val totalResult : Int,
    val totalPages: Int,
    val results: List<Movie>
)
