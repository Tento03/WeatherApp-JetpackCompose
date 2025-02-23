package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key")key:String,
        @Query("q")q:String
    ): WeatherResponse
}