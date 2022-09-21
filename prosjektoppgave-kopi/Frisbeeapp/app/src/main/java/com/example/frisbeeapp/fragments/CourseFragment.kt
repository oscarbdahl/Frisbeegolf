package com.example.frisbeeapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.frisbeeapp.R
import com.example.frisbeeapp.databinding.FragmentCourseBinding
import com.example.frisbeeapp.viewmodel.CourseViewModel

class CourseFragment : Fragment() {

    // bruker viewbinding
    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!

    // delt viewmodel for aa hente course-objekter
    private val model: CourseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setter alle views i fragmentet
        model.singleCourse.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.img).into(binding.coursePhoto)
            binding.textView.text = it.name
            binding.sunText.text = it.courseWeather.temp.toString() + " °C"
            binding.rainText.text = it.courseWeather.precipitation.toString() + " mm"
            binding.windText.text = it.courseWeather.windSpeed.toString() + " m/s (" + it.courseWeather.windDirectionText + ")"

            // legger til beskrivelse
            val beskrivelse : String = "KOLLEKTIV TRANSPORT: ${it.publicTransport}\n" +
                                        "\n VANSKELIGHETSGRAD:  ${it.difficulty}\n" +
                                        "\n TEES: ${it.tees}\n" + "\n WC: ${it.wc}\n"
            binding.courseText.text = beskrivelse

            // Lager drawable objekter av bildefilene
            val clearskyday = ResourcesCompat.getDrawable(resources, R.drawable.clearsky_day, null)
            val fairday = ResourcesCompat.getDrawable(resources, R.drawable.fair_day, null)
            val cloudy = ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)
            val heavyrain = ResourcesCompat.getDrawable(resources, R.drawable.heavyrain, null)

            // endrer symbol til vaer
            when (it.courseWeather.findWeatherSymbol) {
                "cloudy" -> binding.sun.setImageDrawable(cloudy)
                "fair_day" -> binding.sun.setImageDrawable(fairday)
                "heavyrain" -> binding.sun.setImageDrawable(heavyrain)
                else -> binding.sun.setImageDrawable(clearskyday)
            }
        }
        // click listener til trykk paa bilde av banen
        binding.coursePhoto.setOnClickListener {
            findNavController().navigate(R.id.action_courseFragment_to_imageFullScreenFragment)
        }

        // click listener til trykk paa poengkort-knapp
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_courseFragment_to_scoreBoardFragment)
        }
    }

    // setter binding til null
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}