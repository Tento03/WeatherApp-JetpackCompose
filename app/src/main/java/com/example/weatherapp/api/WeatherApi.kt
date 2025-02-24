package com.example.weatherapp.api

import com.example.weatherapp.model.ForecastResponse
import com.example.weatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key")key:String,
        @Query("q")q:String
    ): WeatherResponse

    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("key")key: String,
        @Query("q")q:String,
        @Query("days")days:Int
    ):ForecastResponse
}