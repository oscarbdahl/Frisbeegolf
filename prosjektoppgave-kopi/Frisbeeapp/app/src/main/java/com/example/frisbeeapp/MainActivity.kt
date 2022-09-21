package com.example.frisbeeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.frisbeeapp.databinding.ActivityMainBinding
import com.example.frisbeeapp.viewmodel.CourseViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // delt viewmodel
    private val model: CourseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Frisbeeapp)
        // gjoer kall paa api ved oppstart av app
        model.fetchCourses()
        // bruker viewbinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // oppretter nav-bar og setter navcontroller
        val bottomNavigationView = binding.navBar
        val navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        bottomNavigationView.setupWithNavController(navController)

    }
}
