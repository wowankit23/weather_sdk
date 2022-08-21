package com.mobileprogramming.weather_sdk

import com.mobileprogramming.weather_sdk.data.RemoteDataSource
import com.mobileprogramming.weather_sdk.di.AppModule
import com.mobileprogramming.weather_sdk.repository.WeatherRepository
import org.koin.dsl.module

val libraryModule = module {
    single { RemoteDataSource() }
    single { AppModule.provideAuthApi(get())}
    single { WeatherRepository(get()) }
}