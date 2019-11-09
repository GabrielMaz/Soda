package com.gabrielmaz.soda.todo_lo_otro.models

data class Discover(
    val page: Int,
    val totalResult : Int,
    val totalPages: Int,
    val results: ArrayList<Movie>
)
