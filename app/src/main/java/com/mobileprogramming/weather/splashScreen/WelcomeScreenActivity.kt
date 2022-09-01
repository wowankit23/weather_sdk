package com.mobileprogramming.weather.activity.splashScreen.weather

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mobileprogramming.weather.R
import com.mobileprogramming.weather.databinding.ActivitySplashScreenBinding
import com.mobileprogramming.weather.splashScreen.weather.WeatherDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class WelcomeScreenActivity : AppCompatActivity() {

    private val viewModel : WelcomeScreenViewModel by viewModel()
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

               viewModel.goToWeatherReport(this)

    }

}