package com.example.finalweatherapp.model.utils

import android.os.Build
import android.os.SystemClock
import com.google.type.TimeZone
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {

    fun convertUnixTimestampToPST(unixTimestamp: Long): List<String> {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = java.util.TimeZone.getTimeZone("Asia/Bangkok")
        val utcDate = Date(unixTimestamp * 1000L)
        val dateTime = sdf.format(utcDate)
        val dateComponents = dateTime.split(" ")
        return dateComponents
    }

    fun String.toFormattedDate(): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

        try {
            val date = inputDateFormat.parse(this)
            if (date != null) {
                return outputDateFormat.format(date)
            }
        } finally {

        }
        return this
    }

    fun convertToAmPm(time24: String): String {
        val sdf24 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val sdf12 = SimpleDateFormat("hh a", Locale.getDefault())
        val date24 = sdf24.parse(time24)
        return sdf12.format(date24)
    }



    fun String.toFormattedDay(): String? {
        val dateComponents = this.split("-")
        return if (dateComponents.size == 3) {
            val year = dateComponents[0].toInt()
            val month = dateComponents[1].toInt() - 1
            val day = dateComponents[2].toInt()

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val outputDateFormat = SimpleDateFormat("EE", Locale.getDefault())
            return outputDateFormat.format(calendar.time)
        } else null
    }




}