package com.mobileprogramming.weather.di

import com.mobileprogramming.weather.activity.splashScreen.weather.WeatherDetailViewModel
import com.mobileprogramming.weather.activity.splashScreen.weather.WelcomeScreenViewModel
import com.mobileprogramming.weather_sdk.libraryModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { WeatherDetailViewModel(get()) }
    viewModel { WelcomeScreenViewModel() }
}.plus(libraryModule)
