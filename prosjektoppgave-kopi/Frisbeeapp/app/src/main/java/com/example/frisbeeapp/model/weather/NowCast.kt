package com.example.frisbeeapp.model.weather

data class NowCast(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

