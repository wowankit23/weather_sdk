package com.mobileprogramming.weather_sdk

import com.mobileprogramming.weather_sdk.model.WeatherResponse
import retrofit2.http.*

interface AuthApi {

    @GET("onecall")
    suspend fun getWeatherInfo(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
        @Query("cnt") cnt: String,
    ): WeatherResponse

}