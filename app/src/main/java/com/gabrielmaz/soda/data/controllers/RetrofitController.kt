package com.gabrielmaz.soda.data.controllers

import com.gabrielmaz.soda.App
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitController(private val gsonConverterFactory: GsonConverterFactory) {
    companion object {
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val baseImageUrl = "https://image.tmdb.org/t/p/w500"
        const val apiKey = "6d6e6ac0a910598652be5994ade22a61"
    }

    fun initRetrofit() =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    private val httpClient
        get() = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())

                if (response.code() == 401) {
                    App.goToLoginScreen()
                }

                response
            }
            .build()
}