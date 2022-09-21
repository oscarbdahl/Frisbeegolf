package com.example.frisbeeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frisbeeapp.model.course.Course
import com.example.frisbeeapp.databinding.CourseItemBinding

@Suppress("DEPRECATION")
class CourseAdapter(private val list: List<Course>, private val listener: OnItemClickListener): RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    inner class ViewHolder(binding: CourseItemBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val image = binding.courseImage
        val courseText = binding.courseName

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemClick(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // setter de ulike verdiene i cardviewet
    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val url = list[pos].photo
        Glide.with(holder.itemView.context).load(url).into(holder.image)
        holder.courseText.text = list[pos].name
    }

    override fun getItemCount(): Int { return list.size }

    // interface til onclick på hvert element i recyclerviewet
    interface OnItemClickListener {
        fun onItemClick(course: Course)
    }

}