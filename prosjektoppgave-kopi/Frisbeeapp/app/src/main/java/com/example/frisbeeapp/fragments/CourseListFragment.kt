package com.example.frisbeeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frisbeeapp.viewmodel.CourseViewModel
import com.example.frisbeeapp.R
import com.example.frisbeeapp.adapter.CourseAdapter
import com.example.frisbeeapp.model.course.Course
import com.example.frisbeeapp.databinding.FragmentCourseListBinding


class CourseListFragment : Fragment(), CourseAdapter.OnItemClickListener {

    // bruker viewbinding
    private var _binding: FragmentCourseListBinding? = null
    private val binding get() = _binding!!

    // delt viewmodel til aa hente course-objekter
    private val model: CourseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseListBinding.inflate(inflater, container, false)
        // observer course-objektene fra viewmodel og setter adapter
        model.getCourses().observe(viewLifecycleOwner) {
            binding.recyclerview.adapter = CourseAdapter(it, this)
        }
        // setter layoutmanager til recyclerviewet
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        // skal forhindre recyclerviewet fra aa gaa til toppen
        binding.recyclerview.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        return binding.root
    }

    // setter binding til null
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // onclick til hvert element i recyclerviewet
    override fun onItemClick(course: Course) {
        model.selectedCourse(course)
        findNavController().navigate(R.id.action_courseListFragment_to_courseFragment)
    }

}