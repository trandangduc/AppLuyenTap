package com.example.appluyentap

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val exercises = listOf(
            Exercise("Bật nhảy", "00:20", R.drawable.ic_jump),
            Exercise("Tập cơ bụng", "x16", R.drawable.ic_crunch),
            Exercise("Gập bụng chéo kiểu Nga", "x20", R.drawable.ic_russian_twist),
            Exercise("Leo núi", "x16", R.drawable.ic_mountain_climber),
            Exercise("Chạm gót chân", "x20", R.drawable.ic_heel_touch)
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ExerciseAdapter(exercises)

        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            // Bắt đầu bài tập
        }
    }
}