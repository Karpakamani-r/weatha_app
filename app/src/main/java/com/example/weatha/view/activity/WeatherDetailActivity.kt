package com.example.weatha.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatha.BuildConfig
import com.example.weatha.databinding.ActivityWeatherDetailBinding
import com.example.weatha.util.ApiException
import com.example.weatha.util.Coroutines
import com.example.weatha.util.NoInternetException
import com.example.weatha.viewmodel.WeatherViewModel
import com.example.weatha.viewmodel.WeatherViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

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
            try {
                val res = viewModel.fetchWeather(BuildConfig.Id, BuildConfig.AppId)
                binding.response = res.value
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}