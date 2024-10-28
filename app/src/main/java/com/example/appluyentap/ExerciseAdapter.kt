package com.example.appluyentap

// ExerciseAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseImage: ImageView = itemView.findViewById(R.id.exercise_image)
        val exerciseName: TextView = itemView.findViewById(R.id.exercise_name)
        val exerciseReps: TextView = itemView.findViewById(R.id.exercise_reps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.exerciseImage.setImageResource(exercise.imageResId)
        holder.exerciseName.text = exercise.name
        holder.exerciseReps.text = exercise.repsOrTime
    }

    override fun getItemCount(): Int = exercises.size
}
