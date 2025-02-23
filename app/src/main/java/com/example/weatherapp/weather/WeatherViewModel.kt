package com.example.weatherapp.weather

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}