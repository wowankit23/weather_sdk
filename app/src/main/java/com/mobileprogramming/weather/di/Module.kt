package com.mobileprogramming.weather.di

import com.mobileprogramming.weather.activity.splashScreen.weather.WelcomeScreenViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


    val appModule = module {

        viewModel { WelcomeScreenViewModel() }
    }

