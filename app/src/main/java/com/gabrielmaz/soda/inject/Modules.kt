package com.gabrielmaz.soda.inject

import com.gabrielmaz.soda.data.controllers.DiscoverController
import com.gabrielmaz.soda.data.controllers.RetrofitController
import com.gabrielmaz.soda.data.controllers.ReviewController
import com.gabrielmaz.soda.data.helper.networking.NetworkingManager
import com.gabrielmaz.soda.data.repository.MoviesSourceDataRepository
import com.gabrielmaz.soda.data.repository.MoviesSourceRepository
import com.gabrielmaz.soda.data.repository.movies.MoviesDataStoreFactory
import com.gabrielmaz.soda.data.service.DiscoverService
import com.gabrielmaz.soda.data.service.ReviewService
import com.gabrielmaz.soda.data.sources.AppDatabase
import com.gabrielmaz.soda.presentation.view.discover.DiscoverViewModel
import com.gabrielmaz.soda.presentation.view.movie.MovieDetailViewModel
import com.gabrielmaz.soda.presentation.view.reviews.ReviewViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var networkModule = module {
    single { NetworkingManager(get()) }
    single<GsonConverterFactory> {
        GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        )
    }

    single { RetrofitController(get()) }
    single<Retrofit> { get<RetrofitController>().initRetrofit() }
    single {
        val service = get<Retrofit>().create(DiscoverService::class.java)
        DiscoverController(service)
    }
    single {
        val service = get<Retrofit>().create(ReviewService::class.java)
        ReviewController(service)
    }
}

var databaseModule = module {
    single { AppDatabase.getInstance(get()).movieDao() }
    single { AppDatabase.getInstance(get()).favoriteDao() }
}

var moviesModule = module {
    single { MoviesDataStoreFactory(get(), get(), get()) }
    single<MoviesSourceRepository> { MoviesSourceDataRepository(get()) }

    viewModel { DiscoverViewModel(get(), get(), get(), get()) }
    viewModel { MovieDetailViewModel(get(), get()) }
    viewModel { ReviewViewModel(get()) }
}