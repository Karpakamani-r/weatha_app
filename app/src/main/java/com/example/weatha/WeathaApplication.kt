package com.example.weatha

import android.app.Application
import com.example.weatha.model.network.MyApi
import com.example.weatha.model.network.NetworkConnectionInterceptor
import com.example.weatha.model.repo.WeatherRepository
import com.example.weatha.viewmodel.WeatherViewModel
import com.example.weatha.viewmodel.WeatherViewModelFactory
import com.example.weatha.work.RefreshDataWorker

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeathaApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeathaApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { WeatherRepository(instance(), instance()) }
        bind() from provider { WeatherViewModel(instance()) }
        bind() from provider { WeatherViewModelFactory(instance()) }
    }
}