
// Retrofit interface
package com.example.frisbeeapp.data


import com.example.frisbeeapp.model.weather.NowCast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/weatherapi/nowcast/2.0/complete.json?")
    suspend fun getCurrentWeather(@Query("lat") lat: Double?, @Query("lon") lon: Double?): Response<NowCast>
}
