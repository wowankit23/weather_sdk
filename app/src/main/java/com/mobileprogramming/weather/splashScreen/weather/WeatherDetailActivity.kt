package com.mobileprogramming.weather.splashScreen.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.mobileprogramming.weather.R
import com.mobileprogramming.weather.adapter.WeatherDailyReportAdapter
import com.mobileprogramming.weather.databinding.ActivityWeatherDetailBinding
import com.mobileprogramming.weatherlib.data.model.weather.currentmodels.WeatherResponse
import com.mobileprogramming.weatherlib.data.model.weather.weekmodels.Daily
import com.mobileprogramming.weatherlib.data.model.weather.weekmodels.WeatherWeekResponse
import com.weatherlibrary.sdk.WeatherSDK
import com.weatherlibrary.sdk.utils.Utils.convertTempCelsiusToFahrenheit


class WeatherDetailActivity : AppCompatActivity() {

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
        private const val TAG = "WeatherDetailActivity"
    }
    private var temp = WeatherSDK.TempUnit.FAHRENHEIT
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private var exclude = "hourly,minutely"
    private var cnt = 7
    private lateinit var binding: ActivityWeatherDetailBinding
    private lateinit var weatherDailyReportAdapter: WeatherDailyReportAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var isWeatherInFahrenheit = false
    private lateinit var weatherSDK: WeatherSDK
    private lateinit var dailyWeatherReport: ArrayList<Daily>

    var weatherResponse:WeatherResponse?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        binding.progressbar.visibility = View.VISIBLE



    }


    private fun init() {

        weatherSDK = WeatherSDK.getInstance(getString(R.string.weather_key))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_detail)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        dailyWeatherReport = arrayListOf()
        changeCelsiusToFahrenheit()
        updateDailyWeatherReport()
    }


        @SuppressLint("MissingPermission")
         fun getLastLocation() { // check if permissions are given
            if (checkPermissions()) {
                // check if location is enabled
                if (isLocationEnabled()) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        return
                    }
                    fusedLocationProviderClient?.lastLocation?.addOnCompleteListener { task ->
                        val location = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            setLocation(location)
                        }

                    }
                } else {
                    goToLocationSettings()

                }
            } else {
                // if permissions aren't available,request for permissions
                requestPermissions()
            }


    }

    private  fun changeCelsiusToFahrenheit() {
        binding.toggleCToF.setOnCheckedChangeListener { _, isChecked ->

          var farhenite=  convertTempCelsiusToFahrenheit(binding.tempAndSky.text.toString())
        binding.tempAndSky.text=farhenite

            isWeatherInFahrenheit = isChecked

        }
    }









    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        // Initializing LocationRequest
        // object with appropriate methods
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            setLocation(mLastLocation)
        }
    }
    private fun setLocation(location: Location) {
        latitude = location.latitude
        longitude = location.longitude

        getCurrentWeather()

    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                goToLocationSettings()
            }
        }
    }

    // method to request for permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

    private fun goToLocationSettings() {
        Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_LONG)
            .show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun getCurrentWeather() {

        weatherSDK.getCurrentWeather(latitude, longitude, WeatherSDK.TempUnit.CELSIUS,
            object : WeatherSDK.WeatherDataListener {
                override fun onWeatherResponse(response: WeatherResponse) {
                    binding.tempAndSky.text = response.main.temp.toString()
                    binding.windSpeed.text= response.wind.speed.toString()+" km/Hr"
                    getCurrentWeatherforWeek()
//                    binding.content.tvWeatherData.text = "Weather of "+response.name+"is: \n\n"+response.toString()
                }

                override fun onErrorFetchingData(error: Throwable) {
                }
            })

    }

    private fun getCurrentWeatherforWeek() {
        weatherSDK.getCurrentWeatherforWeek(latitude, longitude,
            temp,
            exclude,
            cnt,
            object : WeatherSDK.WeatherDataListenerforWeek {
                override fun onWeatherResponseforWeek(response: WeatherWeekResponse) {
                    dailyWeatherReport.clear()
                    dailyWeatherReport.addAll(response.daily)
                    updateDailyWeatherReport()
                    binding.progressbar.visibility = View.GONE

                }

                override fun onErrorFetchingData(error: Throwable) {
                    Toast.makeText(this@WeatherDetailActivity, error.message.toString(), Toast.LENGTH_SHORT).show()

                }
            })

    }
    private fun updateDailyWeatherReport()
    {
        weatherDailyReportAdapter = WeatherDailyReportAdapter(this, dailyWeatherReport, isWeatherInFahrenheit)
        binding.dailyWeatherReport.layoutManager = linearLayoutManager
        binding.dailyWeatherReport.adapter = weatherDailyReportAdapter
    }

}