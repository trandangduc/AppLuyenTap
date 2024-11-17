package com.example.appluyentap

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WorkoutSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout_setting)

        // Thiết lập hành động quay lại khi nhấn vào ImageView
        val backButton = findViewById<ImageView>(R.id.imageView6)
        backButton.setOnClickListener {
            onBackPressed() // Quay về trang trước đó
        }

        // Thiết lập padding cho hệ thống thanh điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
