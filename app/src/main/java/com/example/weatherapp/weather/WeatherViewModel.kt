package com.example.weatherapp.weather

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.ForecastResponse
import com.example.weatherapp.model.Hour
import com.example.weatherapp.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private var repository: WeatherRepository):ViewModel() {
    private val _weatherData=MutableStateFlow<WeatherResponse?>(null)
    val weatherData:MutableStateFlow<WeatherResponse?> = _weatherData

    private val _forecastData = MutableStateFlow<List<Hour?>>(emptyList())
    val forecastData: StateFlow<List<Hour?>> = _forecastData

    fun fetchWeather(key:String,q:String) {
        viewModelScope.launch {
            try {
                val weatherList= repository.getCurrentWeather(key,q)
                _weatherData.value=weatherList
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchForecast(key: String,q: String,days:Int){
        viewModelScope.launch {
            try {
                val forecastResponse = repository.getForecastWeather(key, q, days)
                val hours = forecastResponse.forecast.forecastday.flatMap { it.hour
                }
                _forecastData.value = hours
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}