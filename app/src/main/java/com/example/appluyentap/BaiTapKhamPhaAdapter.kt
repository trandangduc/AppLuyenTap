package com.example.appluyentap

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import data.ExerciseKhamPha

class BaiTapKhamPhaAdapter(
    private val context: Context,
    private val exercises: List<ExerciseKhamPha>
) : RecyclerView.Adapter<BaiTapKhamPhaAdapter.BaiTapKhamPhaViewHolder>() {

    inner class BaiTapKhamPhaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName: TextView = itemView.findViewById(R.id.exerciseName)
        val exerciseDetails: TextView = itemView.findViewById(R.id.exerciseReps) // TextView for reps or duration
        val exerciseVideo: VideoView = itemView.findViewById(R.id.exerciseVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaiTapKhamPhaViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.exercise_item, parent, false)
        return BaiTapKhamPhaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaiTapKhamPhaViewHolder, position: Int) {
        val exercise = exercises[position]

        // Set exercise name
        holder.exerciseName.text = exercise.TenBaiTap

        // Set exercise video (if available)
        val videoName = exercise.Video
        videoName?.let {
            val resId = context.resources.getIdentifier(it, "raw", context.packageName)
            if (resId != 0) {
                holder.exerciseVideo.setVideoURI(Uri.parse("android.resource://${context.packageName}/$resId"))
                holder.exerciseVideo.setOnPreparedListener {
                    holder.exerciseVideo.start()
                }
            }
        }

        // Show reps or duration
        val detailsText = when {
            !exercise.SoRep.isNullOrEmpty() -> "x ${exercise.SoRep} cái"
            !exercise.ThoiGian.isNullOrEmpty() -> "${exercise.ThoiGian} giây"
            else -> ""
        }

        holder.exerciseDetails.text = detailsText

    }

    override fun getItemCount(): Int = exercises.size
}
