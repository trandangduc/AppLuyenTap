package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.Exercise

class homepage : AppCompatActivity() {
    private var currentExerciseIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val exercises = listOf(
            Exercise("Bật nhảy", reps = "", time = "00:20", videoRes = R.raw.ic_jump),
            Exercise("Tập cơ bụng", reps = "x16", time = "", videoRes = R.raw.ic_crunch),
            Exercise("Gập bụng chéo kiểu Nga", reps = "x20", time = "", videoRes = R.raw.ic_russian_twist),
            Exercise("Leo núi", reps = "x16", time = "", videoRes = R.raw.ic_mountain_climber),
            Exercise("Chạm gót chân", reps = "x20", time = "", videoRes = R.raw.ic_heel_touch),
            Exercise("Nâng chân", reps = "x16", time = "", videoRes = R.raw.ic_leg_raise),
            Exercise("Đo sàn", reps = "", time = "00:20", videoRes = R.raw.ic_plank)
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ExerciseAdapter(exercises)

        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            if (currentExerciseIndex < exercises.size) {
                val currentExercise = exercises[currentExerciseIndex]

                // Mở ExerciseActivity và truyền bài tập hiện tại
                val intent = Intent(this, ExerciseActivity::class.java)
                intent.putExtra("exercise", currentExercise)
                startActivity(intent)

                currentExerciseIndex++
            } else {
                Toast.makeText(this, "Hoàn thành tất cả bài tập!", Toast.LENGTH_SHORT).show()
                currentExerciseIndex = 0
            }
        }
    }
}
