package com.example.frisbeeapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val baseUrl = "https://in2000-apiproxy.ifi.uio.no"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // maa legge til converter factory for aa
            // konvertere JSON objekt til Java objekt
            .build()
    }
}