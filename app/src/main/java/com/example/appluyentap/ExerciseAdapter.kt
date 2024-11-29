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
import data.Exercise
import data.Initiate
import data.Stretching

class ExerciseAdapter(
    private val context: Context,
    private val mainExercises: List<Exercise>,
    private val stretchingExercises: List<Stretching>,
    private val warmupExercises: List<Initiate>
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName: TextView = itemView.findViewById(R.id.exerciseName)
        val exerciseVideo: VideoView = itemView.findViewById(R.id.exerciseVideo)
        val exerciseDetails: TextView = itemView.findViewById(R.id.exerciseReps) // TextView for repetitions or duration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        // Determine which list the exercise belongs to, in the order: Warmup, Main exercises, Stretching
        val exercise: Any = when {
            position < warmupExercises.size -> warmupExercises[position] // Warmup exercises
            position < warmupExercises.size + mainExercises.size -> mainExercises[position - warmupExercises.size] // Main exercises
            else -> stretchingExercises[position - warmupExercises.size - mainExercises.size] // Stretching exercises
        }

        // Set exercise name
        when (exercise) {
            is Exercise -> holder.exerciseName.text = exercise.TenBaiTap
            is Stretching -> holder.exerciseName.text = exercise.TenBaiTap
            is Initiate -> holder.exerciseName.text = exercise.TenBaiTap
        }

        // Get video name from the exercise object (assuming Video field contains the file name in raw)
        val videoName: String? = when (exercise) {
            is Exercise -> exercise.Video
            is Stretching -> exercise.Video
            is Initiate -> exercise.Video
            else -> null
        }

        // If video name exists, load it from raw resource
        videoName?.let {
            val resId = context.resources.getIdentifier(it, "raw", context.packageName)
            if (resId != 0) {  // If the resource exists
                holder.exerciseVideo.setVideoURI(Uri.parse("android.resource://${context.packageName}/$resId"))
                holder.exerciseVideo.setOnPreparedListener {
                    holder.exerciseVideo.start() // Start video automatically
                }
            }
        }

        // Display repetitions for main exercises or duration for stretching and warmup exercises
        val detailsText: String = when (exercise) {
            is Exercise -> "x ${exercise.SoRep} cái" // For main exercises, show repetitions
            is Stretching -> "${exercise.ThoiGian} giây" // For stretching exercises, show time duration
            is Initiate -> "${exercise.ThoiGian} giây" // For warmup exercises, show time duration
            else -> ""
        }

        holder.exerciseDetails.text = detailsText


    }

    override fun getItemCount(): Int {
        return warmupExercises.size + mainExercises.size + stretchingExercises.size
    }
}
