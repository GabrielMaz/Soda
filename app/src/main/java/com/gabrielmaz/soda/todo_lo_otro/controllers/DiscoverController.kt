package com.gabrielmaz.soda.todo_lo_otro.controllers

import com.gabrielmaz.soda.todo_lo_otro.services.DiscoverService

class DiscoverController {
    private val discoverService = RetrofitController.retrofit.create(DiscoverService::class.java)

    suspend fun getDiscovers() = discoverService.getDiscovers(RetrofitController.apiKey)
}