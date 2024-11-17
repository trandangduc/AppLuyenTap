package com.example.appluyentap

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import data.Exercise

class ExerciseAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.exerciseName)
        val repsTextView: TextView = itemView.findViewById(R.id.exerciseReps)
        val videoView: VideoView = itemView.findViewById(R.id.exerciseVideo)

        // Hàm dừng phát video
        fun stopVideo() {
            if (videoView.isPlaying) {
                videoView.stopPlayback()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.nameTextView.text = exercise.name
        holder.repsTextView.text = exercise.reps

        exercise.videoRes?.let { videoRes ->
            val videoUri = Uri.parse("android.resource://${holder.itemView.context.packageName}/$videoRes")
            holder.videoView.setVideoURI(videoUri)
            holder.videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true // Video sẽ lặp lại
                holder.videoView.start() // Bắt đầu phát video
            }
        }
    }

    override fun getItemCount() = exercises.size

    // Dừng video khi ViewHolder bị tái chế
    override fun onViewRecycled(holder: ExerciseViewHolder) {
        super.onViewRecycled(holder)
        holder.stopVideo()
    }
}
