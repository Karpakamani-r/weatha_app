package com.example.weatha.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatha.R
import java.text.DateFormat
import java.util.*
import kotlin.math.roundToLong

class Utils {
    companion object {
        /**
         * Converting celsius from kelvin
         * @param Kelvin Value
         * @return Celsius Value
         */
        @RequiresApi(Build.VERSION_CODES.N)
        @JvmStatic
        fun getCelsiusFromKelvin(kelvin: Double): String {
            val number2digits: Double = ((kelvin - 273.15) * 100.0).roundToLong() / 100.0
            return "$number2digits°C"
        }

        /**
         * Converting unix time to date format
         * @param time - Unix Time
         * @return time as String
         */
        @JvmStatic
        fun getTimeFromUnixTime(time: Long): String {
            val fDate = DateFormat.getTimeInstance(DateFormat.SHORT)
            fDate.timeZone = getTimeZoneInstance()
            return fDate.format(getDateFromUNIXTime(time))
        }

        /**
         * Converting unix date to date format
         * @param time - Unix Time
         * @return date as String
         */
        @JvmStatic
        fun getDateFromUnixTime(time: Long): String {
            val fDate = DateFormat.getDateInstance(DateFormat.MEDIUM)
            fDate.timeZone = getTimeZoneInstance()
            return fDate.format(getDateFromUNIXTime(time))
        }

        /**
         *Formatting double value with kmph
         * @param wSpeed Speed
         * @return speed with kmph
         */
        @JvmStatic
        fun getWindSpeedByKPHr(context: Context, wSpeed: Double): String {
            return String.format(
                context.getString(R.string.wind_speed_value),
                wSpeed
            )
        }

        /**
         * To Get Ireland's time zone
         * @return TimeZone(Ireland)
         */
        @JvmStatic
        private fun getTimeZoneInstance(): TimeZone {
            return TimeZone.getTimeZone("GMT1")
        }

        /**
         * Converting unix to date object
         * @param unixTime
         * @return Date
         */
        @JvmStatic
        private fun getDateFromUNIXTime(unixTime: Long): Date {
            return Date(unixTime * 1000)
        }
    }
}