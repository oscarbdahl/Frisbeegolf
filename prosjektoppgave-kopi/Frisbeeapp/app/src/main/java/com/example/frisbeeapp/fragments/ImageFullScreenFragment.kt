package com.example.frisbeeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.frisbeeapp.databinding.FragmentImageFullScreenBinding
import com.example.frisbeeapp.viewmodel.CourseViewModel


class ImageFullScreenFragment : Fragment() {

    // bruker viewbinding
    private var _binding: FragmentImageFullScreenBinding? = null
    private val binding get() = _binding!!

    // delt viewmodel for aa henter course-objekter
    private val model: CourseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageFullScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observer livedata med course objekt
        model.singleCourse.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.img).into(binding.courseFull)
        }

    }

}