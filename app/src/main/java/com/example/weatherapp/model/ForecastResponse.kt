package com.example.weatherapp.model

data class ForecastResponse(
    val current: CurrentX,
    val forecast: Forecast,
    val location: LocationX
)