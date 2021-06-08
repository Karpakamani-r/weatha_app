package com.example.weatha.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatha.BuildConfig
import com.example.weatha.databinding.ActivityWeatherDetailBinding
import com.example.weatha.util.Coroutines
import com.example.weatha.viewmodel.WeatherViewModel
import com.example.weatha.viewmodel.WeatherViewModelFactory
import com.example.weatha.work.RefreshDataWorker
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class WeatherDetailActivity() : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: WeatherViewModelFactory by instance()

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)

        Coroutines.main {
            val res = viewModel.fetchWeather(BuildConfig.Id, BuildConfig.AppId)
            binding.response = res.value
        }
    }
}