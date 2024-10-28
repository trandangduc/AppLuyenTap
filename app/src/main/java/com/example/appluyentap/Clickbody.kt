package com.example.appluyentap

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Clickbody : AppCompatActivity() {
    // Biến trạng thái để kiểm tra xem nút có đang ở trạng thái đã nhấn hay không
    private var isButton1Clicked = false
    private var isButton2Clicked = false
    private var isButton3Clicked = false
    private var isButton4Clicked = false
    private var isButton5Clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_clickbody)

        // Xử lý insets cho hệ thống thanh trạng thái và điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tìm các nút trong layout và đặt sự kiện click
        val button1: Button = findViewById(R.id.button12)
        val button2: Button = findViewById(R.id.button14)
        val button3: Button = findViewById(R.id.button15)
        val button4: Button = findViewById(R.id.button16)
        val button5: Button = findViewById(R.id.button17)

        // Sự kiện nhấn cho button1
        button1.setOnClickListener {
            isButton1Clicked = !isButton1Clicked
            if (isButton1Clicked) {
                button1.setBackgroundColor(Color.parseColor("#673AB7")) // Màu tím
            } else {
                button1.setBackgroundColor(Color.WHITE) // Màu mặc định là màu trắng
            }
        }

        // Sự kiện nhấn cho button2
        button2.setOnClickListener {
            isButton2Clicked = !isButton2Clicked
            if (isButton2Clicked) {
                button2.setBackgroundColor(Color.parseColor("#673AB7"))
            } else {
                button2.setBackgroundColor(Color.WHITE)
            }
        }

        // Sự kiện nhấn cho button3
        button3.setOnClickListener {
            isButton3Clicked = !isButton3Clicked
            if (isButton3Clicked) {
                button3.setBackgroundColor(Color.parseColor("#673AB7"))
            } else {
                button3.setBackgroundColor(Color.WHITE)
            }
        }

        // Sự kiện nhấn cho button4
        button4.setOnClickListener {
            isButton4Clicked = !isButton4Clicked
            if (isButton4Clicked) {
                button4.setBackgroundColor(Color.parseColor("#673AB7"))
            } else {
                button4.setBackgroundColor(Color.WHITE)
            }
        }

        // Sự kiện nhấn cho button5
        button5.setOnClickListener {
            isButton5Clicked = !isButton5Clicked
            if (isButton5Clicked) {
                button5.setBackgroundColor(Color.parseColor("#673AB7"))
            } else {
                button5.setBackgroundColor(Color.WHITE)
            }
        }
    }
}
