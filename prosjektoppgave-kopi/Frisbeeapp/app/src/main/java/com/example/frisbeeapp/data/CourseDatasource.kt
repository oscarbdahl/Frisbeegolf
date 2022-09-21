package com.example.frisbeeapp.data

import  android.util.Log
import com.example.frisbeeapp.model.course.Course
import com.example.frisbeeapp.model.weather.CourseWeather
import com.example.frisbeeapp.model.course.Courses
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import java.lang.Exception

class CourseDatasource {

    private var courses = listOf<Course>()
    private val coursesPath = "https://api.npoint.io/06bb94aec8daf6bbf9df"

    // gjoer kall paa eget api ved hjelp av fuel
    // sender resultat videre til getCourseWithWeather
    // returnerer course objektene med vaer lagt til i objektene
    suspend fun courseApiCall(): List<Course> {
        if (courses.isNotEmpty()) {
            return courses
        }
        try {
            courses = Gson().fromJson(Fuel.get(coursesPath).awaitString(), Courses::class.java).courses
        }
        catch (e: Exception) {
            Log.d("Gikk ikke laste inn Course-objekter", e.toString())
        }
        return getCourseWithWeather(courses)

    }

    // gjoer kall paa met api
    private suspend fun getCourseWithWeather(courses: List<Course>): List<Course> {
        val weatherService = RetrofitHelper.getInstance().create(WeatherService::class.java)

        for (course in courses) {
            val lat = String.format("%.2f", course.latitude)
            val lon = String.format("%.2f", course.longitude)

            val result = weatherService.getCurrentWeather(lat.toDouble(), lon.toDouble())
                .body()!!
                .properties
                .timeseries[0]
                .data
                .instant
                .details

            val weatherSymbolCode = weatherService.getCurrentWeather(lat.toDouble(), lon.toDouble())
                .body()!!
                .properties .timeseries[0]
                .data .next_1_hours .summary .symbol_code

            val courseWeather = CourseWeather(lat.toDouble(), lon.toDouble(), result.air_temperature, result.wind_speed, result.precipitation_rate ,
                weatherSymbolCode,
                findWeatherSymbol(weatherSymbolCode), findWindDirection(result.wind_from_direction))

            course.courseWeather = courseWeather
        }
        return courses
    }

    // Gaar ut i fra kun 4 vindpiler
    private fun findWindDirection(windDirection: Double): String {
        var windDirectionText = ""
        when (windDirection){
            in 0.0 .. 22.5 -> windDirectionText = "N"
            in 337.6 .. 360.0 -> windDirectionText = "N"
            in 22.6 .. 67.5 -> windDirectionText = "NØ"
            in 67.6 .. 112.5 -> windDirectionText = "Ø"
            in 112.6 .. 157.5-> windDirectionText = "SØ"
            in 157.6 .. 202.5 -> windDirectionText = "S"
            in 202.6 .. 247.5 -> windDirectionText = "SV"
            in 247.6 .. 292.5 -> windDirectionText = "V"
            in 292.6 .. 337.5 -> windDirectionText = "NV"
        }
        return windDirectionText
    }

    // sammenslaar vaersymboler
    private fun findWeatherSymbol(symbol_code: String): String {
        val fairDayList = listOf("fair", "partlycloudy")
        val heavyRainList = listOf("rain", "rainandthunder", "rainshowers", "rainshowersandthunder", "sleet", "sleetandthunder",
            "sleetshowers", "sleetshowersandthunder", "snow", "snowandthunder", "snowshowers", "snowshowersandthunder",
            "heavyrain", "heavyrainandthunder", "heavyrainshowers", "heavyrainshowersandthunder", "heavysleet",
            "heavysleetandthunder", "heavysleetshowers", "heavysleetshowersandthunder", "heavysnow", "heavysnowandthunder",
            "heavysnowshowers", "lightrain", "lightrainandthunder", "lightrainshowers", "lightrainshowersandthunder", "lightsleet",
            "lightsleetandthunder", "lightsleetshowers", "lightsnow", "lightsnowandthunder", "lightsnowshowers", "lightssleetshowersandthunder", "lightssnowshowersandthunder")

        val cloudyList = listOf("cloudy", "fog")
        val clearskyList = listOf("clearsky")

        return when (symbol_code) {
            in fairDayList -> "fair_day"
            in heavyRainList -> "heavyrain"
            in cloudyList -> "cloudy"
            in clearskyList -> "clearsky_day"
            else -> "None"
        }
    }
}