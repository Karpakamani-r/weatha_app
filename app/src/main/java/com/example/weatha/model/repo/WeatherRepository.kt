package com.example.weatha.model.repo

import android.content.Context
import com.example.weatha.model.network.MyApi
import com.example.weatha.model.network.SafeApiRequest
import com.example.weatha.model.network.responses.WeatherResponse
import com.google.gson.Gson
import java.io.File

class WeatherRepository(
    private val api: MyApi, private val context: Context
) : SafeApiRequest() {

    suspend fun getWeatherInfo(id: String, appId: String): WeatherResponse {
        var weatherResponse = getWeatherFromLocal()
        if (weatherResponse == null) {
            weatherResponse = updateAndGetResponse(id, appId)
        }
        return weatherResponse
    }

    suspend fun updateAndGetResponse(id: String, appId: String): WeatherResponse {
        val weatherResponse = apiRequest {
            api.fetchWeather(id, appId)
        }
        saveLocally(weatherResponse)
        return weatherResponse
    }

    private fun getWeatherFromLocal(): WeatherResponse? {
        val jsonFile = File(context.cacheDir, "Weather.json").readText(Charsets.UTF_8)
        return Gson().fromJson<WeatherResponse>(jsonFile, WeatherResponse::class.java)
    }

    private fun saveLocally(weatherResponse: WeatherResponse) {
        val file = File(context.cacheDir, "Weather.json")
        file.deleteOnExit()
        file.createNewFile()
        val jsonData = Gson().toJson(weatherResponse)
        file.bufferedWriter().use { out ->
            out.write(jsonData)
        }
    }
}