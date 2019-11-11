package com.gabrielmaz.soda.data.controllers

import com.gabrielmaz.soda.data.service.DiscoverService

class DiscoverController {
    private val discoverService = RetrofitController.retrofit.create(DiscoverService::class.java)

    suspend fun getDiscovers() = discoverService.getDiscovers(RetrofitController.apiKey)

    suspend fun getDiscoversByName(name: String) = discoverService.getDiscoversByName(RetrofitController.apiKey, name)

    suspend fun getDiscoversByRate(min: Int, max: Int) = discoverService.getDiscoversByRate(RetrofitController.apiKey, min, max)
}