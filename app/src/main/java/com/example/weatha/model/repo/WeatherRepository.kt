package com.example.weatha.model.repo

import android.content.Context
import android.util.Log
import com.example.weatha.model.network.MyApi
import com.example.weatha.model.network.SafeApiRequest
import com.example.weatha.model.network.responses.WeatherResponse
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException

class WeatherRepository(
    private val api: MyApi, private val context: Context
) : SafeApiRequest() {

    /**
     * Getting Weather details
     * First checking in local file, If we have data in Local
     * Returning local value otherwise getting from remote server
     * @param id - ID
     * @param appId -App ID
     */
    suspend fun getWeatherInfo(id: String, appId: String): WeatherResponse {
        var weatherResponse = getWeatherFromLocal()
        if (weatherResponse == null) {
            weatherResponse = updateAndGetResponse(id, appId)
        }
        return weatherResponse
    }

    /**
     * Getting data from Remote Server
     * @param id ID
     * @param appId App id
     * @return weatherResponse
     */
    suspend fun updateAndGetResponse(id: String, appId: String): WeatherResponse {
        val weatherResponse = apiRequest {
            api.fetchWeather(id, appId)
        }
        saveLocally(weatherResponse)
        return weatherResponse
    }

    /**
     * Retrying data from local json file
     * @return WeatherResponse
     */
    private fun getWeatherFromLocal(): WeatherResponse? {
        return try {
            val jsonFile = File(context.cacheDir, "Weather.json").readText(Charsets.UTF_8)
            Gson().fromJson<WeatherResponse>(jsonFile, WeatherResponse::class.java)
        } catch (fileNotFoundException: FileNotFoundException) {
            Log.e("Exception: ", fileNotFoundException.message.toString())
            null
        }

    }

    /**
     *Saving response data as string in local json file
     * @param weatherResponse Response
     */
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