package com.mobileprogramming.weatherlib.data.network


import com.mobileprogramming.weatherlib.data.model.weather.currentmodels.WeatherResponse
import com.mobileprogramming.weatherlib.data.model.weather.weekmodels.WeatherWeekResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") tempUnit: String,
        @Query("appid") apiKey: String
    ): Single<WeatherResponse>


    @GET("onecall")
    fun getCurrentWeatherforWeek(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") tempUnit: String,
        @Query("exclude") exclude: String,
        @Query("cnt") cnt: Int,
        @Query("appid") apiKey: String
    ): Single<WeatherWeekResponse>

}