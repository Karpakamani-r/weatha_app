package com.example.weatha.model.network

import com.example.weatha.BuildConfig
import com.example.weatha.model.network.responses.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("data/2.5/weather?")
    suspend fun fetchWeather(
        @Query("id") id: String,
        @Query("appid") appId: String
    ): Response<WeatherResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BuildConfig.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

