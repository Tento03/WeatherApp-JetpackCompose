package com.example.weatherapp.weather

import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.model.ForecastResponse
import com.example.weatherapp.model.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private var weatherApi: WeatherApi) {
    suspend fun getCurrentWeather(key:String,q:String): WeatherResponse {
        return weatherApi.getCurrentWeather(key,q)
    }
    suspend fun getForecastWeather(key: String,q: String,days:Int): ForecastResponse {
        return weatherApi.getForecastWeather(key,q, days)
    }
}