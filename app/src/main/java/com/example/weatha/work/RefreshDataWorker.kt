package com.example.weatha.work

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.weatha.BuildConfig
import com.example.weatha.model.network.MyApi
import com.example.weatha.model.repo.WeatherRepository
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.HttpException
import java.util.concurrent.TimeUnit


class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KodeinAware {

    override val kodein by kodein { appContext }

    private val api: MyApi by instance()

    var repo: WeatherRepository = WeatherRepository(api, appContext)

    override suspend fun doWork(): Result {
        try {
            repo.updateAndGetResponse(BuildConfig.Id, BuildConfig.AppId)
            Log.d("WorkManager: ", "Work request for sync is run")
            startRefreshWork()
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }

    /**
     * Calling this method to start Work to refresh API
     */
    private fun startRefreshWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) //Works,If wifi is connected
            .setRequiredNetworkType(NetworkType.CONNECTED) //Works,If Internet is available
            .build()

        val work = OneTimeWorkRequestBuilder<RefreshDataWorker>()
            .setConstraints(constraints)
            .setInitialDelay(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance().enqueue(
            work
        )
    }
}