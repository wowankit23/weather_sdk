package com.mobileprogramming.weather_sdk.repository
import com.mobileprogramming.weather_sdk.AuthApi
import javax.inject.Inject

public class WeatherRepository  (private val api : AuthApi) : BaseRepository() {


    suspend fun getWeatherInformation(
        lat: String,
        lon: String,
        exclude: String,
        appid: String,
        units: String,
        cnt: String
    ) = safeApiCall {
        api.getWeatherInfo(lat,lon,exclude,appid,units,cnt)
    }

}