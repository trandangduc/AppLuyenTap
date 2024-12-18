package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)

        // Áp dụng WindowInsets để điều chỉnh các thanh trạng thái và điều hướng.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Thay vì bắt sự kiện từ TextView, bắt sự kiện từ LinearLayout
        findViewById<LinearLayout>(R.id.layoutWorkoutSetting).setOnClickListener {
            val intent = Intent(this, WorkoutSetting::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.layoutGeneralSetting).setOnClickListener {
            val intent = Intent(this, GeneralSetting::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.layoutTTSSetting).setOnClickListener {
            val intent = Intent(this, FeedBack::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.layoutAssess).setOnClickListener {
            val intent = Intent(this, Assess::class.java)
            startActivity(intent)
        }
    }
}