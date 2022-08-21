package com.example.weatherreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.R
import com.example.weatherreport.databinding.WeatherDailyItemBinding
import com.example.weather.model.weatherModel.Daily
import com.example.weather.utils.DateUtils.getDateFromTimeStamp
import com.example.weather.utils.TemperatureUtils.convertTempCelsiusToFahrenheit
import com.mobileprogramming.weather.R
import com.mobileprogramming.weather.databinding.WeatherDailyItemBinding
import com.mobileprogramming.weather_sdk.model.Daily

class WeatherDailyReportAdapter(
    var context: Context,
    private val list: ArrayList<Daily>,
    private val isWeatherInFahrenheit : Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding: WeatherDailyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.weather_daily_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bindData(list[position])
            else -> throw IllegalArgumentException()
        }

    }

    inner class ViewHolder(var binding: WeatherDailyItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(
            item: Daily
        ) {

            binding.item = item
            binding.date.text = getDateFromTimeStamp(item.dt?.toLong())
            binding.tempAndSky.text = item.weather[0].description
            binding.windSpeed.text = item.windSpeed?.toString() + "Km"
            if(isWeatherInFahrenheit) {
                binding.temperature.text = convertTempCelsiusToFahrenheit(item.temp?.day.toString())  + "F'"
            }
            else {
                binding.temperature.text = item.temp?.day?.toString() + "Cel"

            }

        }

    }



}