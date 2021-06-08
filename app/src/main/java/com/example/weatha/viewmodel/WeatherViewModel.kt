package com.example.weatha.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatha.model.network.responses.WeatherResponse
import com.example.weatha.model.repo.WeatherRepository
import com.example.weatha.work.RefreshDataWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {
    var liveData: MutableLiveData<WeatherResponse>? = null
    suspend fun fetchWeather(id: String, appId: String): MutableLiveData<WeatherResponse> {
        withContext(Dispatchers.IO) {
            val res = repo.getWeatherInfo(id, appId)
            liveData = MutableLiveData()
            liveData!!.postValue(res)
            //startWork()
        }
        return liveData!!
    }

    private fun startWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work = PeriodicWorkRequestBuilder<RefreshDataWorker>(2, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueuePeriodicWork(work)
    }
}