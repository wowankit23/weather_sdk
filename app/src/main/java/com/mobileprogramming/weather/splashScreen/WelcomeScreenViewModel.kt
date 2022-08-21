package com.mobileprogramming.weather.activity.splashScreen.weather

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.mobileprogramming.weather.splashScreen.weather.WeatherDetailActivity
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class WelcomeScreenViewModel (): ViewModel() {

    fun goToWeatherReport(activity: Activity)
    {
        val backgroundExecutor: ScheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor()
        backgroundExecutor.schedule({

            activity.startActivity(Intent(activity, WeatherDetailActivity::class.java))
            activity.finish()

        }, 3, TimeUnit.SECONDS)
    }
}