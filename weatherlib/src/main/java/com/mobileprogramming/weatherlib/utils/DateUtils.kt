package com.mobileprogramming.weatherlib.utils

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    fun getDateFromTimeStamp(timeStamp: Long?): String {
        val date = Date(timeStamp?.times(1000) ?: 0)
        return SimpleDateFormat("dd-MMM-yyy").format(date)
    }
}