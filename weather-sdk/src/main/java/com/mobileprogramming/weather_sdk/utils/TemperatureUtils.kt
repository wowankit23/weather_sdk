package com.mobileprogramming.weather_sdk.utils

object TemperatureUtils {

    fun convertTempCelsiusToFahrenheit(celsius : String) : String
    {

       val fahrenheit = (celsius.toDouble() * 9/5) + 32

        return String.format("%.2f", fahrenheit)
    }
}