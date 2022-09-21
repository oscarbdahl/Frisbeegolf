package com.example.frisbeeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frisbeeapp.data.CourseDatasource
import com.example.frisbeeapp.model.course.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel: ViewModel() {

    private val courseDatasource = CourseDatasource()
    private val courses = MutableLiveData<List<Course>>()
    val singleCourse = MutableLiveData<Course>()

    // henter listen med course objekter fra livedata objektet
    fun getCourses(): LiveData<List<Course>> {
        return courses
    }

    // gjoer kall paa api asynkront, legger til i courses
    fun fetchCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            courses.postValue(courseDatasource.courseApiCall())
        }
    }

    // returnerer et enkelt course objekt
    // brukes naar man trykker inn på et course i CourseListFragment
    fun selectedCourse(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            singleCourse.postValue(course)
        }
    }

}