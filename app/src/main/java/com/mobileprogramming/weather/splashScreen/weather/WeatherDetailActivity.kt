package com.mobileprogramming.weather.splashScreen.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherreport.adapter.WeatherDailyReportAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mobileprogramming.weather.R
import com.mobileprogramming.weather.activity.splashScreen.weather.WeatherDetailViewModel
import com.mobileprogramming.weather.databinding.ActivityWeatherDetailBinding
import com.mobileprogramming.weather_sdk.model.Daily
import com.mobileprogramming.weather_sdk.model.WeatherResponse


class WeatherDetailActivity : AppCompatActivity() {

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    private val viewModel: WeatherDetailViewModel by viewModel()
    private lateinit var binding: ActivityWeatherDetailBinding
    private lateinit var weatherDailyReportAdapter: WeatherDailyReportAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dailyWeatherReport: ArrayList<Daily>
    private var isWeatherInFahrenheit = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var weatherResponse: WeatherResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_detail)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.progressbar.visibility = View.VISIBLE
        weatherResponse = WeatherResponse()
        dailyWeatherReport = arrayListOf()
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        checkLocationPermission()
        init()
        changeCelsiusToFahrenheit()

    }


    private fun init() {
        viewModel.weatherResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    weatherResponse = it.value
                    updateView(weatherResponse)
                }
                is Resource.Failure -> {
                    Toast.makeText(this, it.errorBody.toString(), Toast.LENGTH_SHORT).show()
                    binding.progressbar.visibility = View.GONE
                }
            }
        }
    }

    private  fun changeCelsiusToFahrenheit() {
        binding.toggleCToF.setOnCheckedChangeListener { _, isChecked ->
            isWeatherInFahrenheit = isChecked

            updateView(weatherResponse)
        }
    }

    private fun updateView(weatherResponse: WeatherResponse) {


        dailyWeatherReport.clear()
        dailyWeatherReport.addAll(weatherResponse.daily)
        updateDailyWeatherReport()
        binding.progressbar.visibility = View.GONE

        binding.tempAndSky.text =
            weatherResponse.current?.weather?.get(0)?.description
        binding.windSpeed.text = getString(R.string.wind_speed) + weatherResponse.current?.windSpeed?.toString() + getString(
           R.string.km
        )
        if(isWeatherInFahrenheit) {
            binding.temperature.text =
                getString(com.example.weather.R.string.temprature) + convertTempCelsiusToFahrenheit(weatherResponse.current?.temp.toString()) + "F'"
        }else {
            binding.temperature.text =
                getString(com.example.weather.R.string.temprature) + weatherResponse.current?.temp?.toString() + getString(
                   R.string.cel
                )
        }

    }


    private fun updateDailyWeatherReport()
    {
        weatherDailyReportAdapter = WeatherDailyReportAdapter(this, dailyWeatherReport, isWeatherInFahrenheit)
        binding.dailyWeatherReport.layoutManager = linearLayoutManager
        binding.dailyWeatherReport.adapter = weatherDailyReportAdapter
    }


    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                requestLocationPermission()
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                viewModel.getWeatherInfo(
                                    location?.latitude.toString(),
                                    location?.longitude.toString()
                                )
                            }
                    }

                } else {

                    Toast.makeText(this, "permission denied! Please allow permission to see weather report", Toast.LENGTH_LONG).show()

                }
                return

            }
        }
    }

}