package com.example.weatha.util

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.weatha.R
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.util.*


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

@RequiresApi(Build.VERSION_CODES.N)
fun getCelsiusFromPH(ph: Double): String {
    return ((ph - 32) * 5 / 9).toInt().toString() + "°C"
}

@RequiresApi(Build.VERSION_CODES.N)
fun getCelsiusFromKelvin(kelvin: Double): String {
    return (kelvin - 273.15).toInt().toString() + "°C"
}

fun getTimeFromUnixTime(time: Long): String {
    val date = Date(time * 1000)
    val timeZone = TimeZone.getTimeZone("GMT1")
    val fdate = DateFormat.getTimeInstance(DateFormat.SHORT)
    fdate.timeZone = timeZone
    return fdate.format(date)
}

fun getDateFromUnixTime(time: Long): String {
    val date = Date(time * 1000)
    val timeZone = TimeZone.getTimeZone("GMT1")
    val fdate = DateFormat.getDateInstance(DateFormat.MEDIUM)
    fdate.timeZone = timeZone
    return fdate.format(date)
}

fun getWindSpeedByKPHr(context: Context, wSpeed: Double): String =
    String.format(
        context.getString(R.string.wind_speed_value),
        wSpeed.toInt()
    )
