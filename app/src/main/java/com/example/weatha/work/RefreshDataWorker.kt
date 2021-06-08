package com.example.weatha.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatha.BuildConfig
import com.example.weatha.model.network.MyApi
import com.example.weatha.model.repo.WeatherRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.kodein
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters, api: MyApi) :
    CoroutineWorker(appContext, params) {

    var repo: WeatherRepository = WeatherRepository(api, appContext)

    companion object {
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        try {
            repo.updateAndGetResponse(BuildConfig.Id, BuildConfig.AppId)
            Log.d("WorkManager: ", "Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }
}