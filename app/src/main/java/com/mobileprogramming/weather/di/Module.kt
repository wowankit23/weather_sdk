package com.example.weatherreport.di

import com.example.weather.libraryModule
import com.example.weatherreport.activity.splashScreen.WelcomeScreenViewModel
import com.example.weatherreport.activity.splashScreen.weather.WeatherDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { WeatherDetailViewModel(get()) }
    viewModel { WelcomeScreenViewModel() }
}.plus(libraryModule)
