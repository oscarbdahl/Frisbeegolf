package com.example.frisbeeapp.model.weather

data class CourseWeather(
    val latitude: Double?,
    val longitude: Double?,
    val temp: Double?,
    val windSpeed: Double?,
    val precipitation: Double?,
    val weatherSymbolCode: String?,
    val findWeatherSymbol: String?,
    val windDirectionText: String?
)
