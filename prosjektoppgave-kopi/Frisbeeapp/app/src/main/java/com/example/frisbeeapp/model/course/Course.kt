package com.example.frisbeeapp.model.course

import com.example.frisbeeapp.model.weather.CourseWeather

// inneholder alle verdier fra eget api
data class Course(
    val name: String?,
    val img: String?,
    val logo: String?,
    val photo: String?,
    val holes: Int?,
    val latitude: Double?,
    val longitude: Double?,
    val par: Int?,
    var courseWeather: CourseWeather,
    val publicTransport: String?,
    val difficulty: String?,
    val tees : String?,
    val wc : String?
)

data class Courses(
    val courses: List<Course>
)
