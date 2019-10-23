package com.gabrielmaz.soda.controllers

import com.gabrielmaz.soda.services.DiscoverService

class DiscoverController {
    private val discoverService = RetrofitController.retrofit.create(DiscoverService::class.java)

    suspend fun getDiscovers() = discoverService.getDiscovers(RetrofitController.apiKey)
}