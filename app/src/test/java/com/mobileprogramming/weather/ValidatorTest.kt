package com.mobileprogramming.weather

import com.mobileprogramming.weather_sdk.model.Weather
import com.mobileprogramming.weather_sdk.repository.WeatherRepository
import com.mobileprogramming.weather_sdk.utils.TemperatureUtils
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.xml.validation.Validator

@RunWith(JUnit4::class)
class ValidatorTest{
    lateinit var repo : WeatherRepository
    private val api_key  = "ae1c4977a943a50eaa7da25e6258d8b2"
    private val latitude =27.34345
    private val longitude =77.343543
var tempUtils: Any?=null


    @Before
    fun init() {
        tempUtils=TemperatureUtils
    }

    @Test
    fun whenLocationisvalid(){
        val result = TemperatureUtils.convertTempCelsiusToFahrenheit("37")
        val expected_data = "98.60000"
        assertThat(result,true).equals(expected_data)
    }




    @Test
    fun testweatherresponse() = runBlocking{

    }
}