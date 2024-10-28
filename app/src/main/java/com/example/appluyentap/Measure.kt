package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonBack: ImageButton
    private lateinit var buttonSkip: Button
    private lateinit var btnNext: Button
    private lateinit var buttonBeginner: Button
    private lateinit var buttonIntermediate: Button
    private lateinit var buttonAdvanced: Button

    // Biến lưu trữ câu trả lời đã chọn
    private var selectedAnswer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        // Liên kết các thành phần từ XML
        buttonBack = findViewById(R.id.button_back)
        buttonSkip = findViewById(R.id.button_skip)
        btnNext = findViewById(R.id.btn_next)
        buttonBeginner = findViewById(R.id.button_beginner)
        buttonIntermediate = findViewById(R.id.button_intermediate)
        buttonAdvanced = findViewById(R.id.button_advanced)

        // Xử lý sự kiện cho nút "Quay trở về"
        buttonBack.setOnClickListener {
            // Đóng activity hiện tại, quay về activity trước
            finish()
        }

        // Xử lý sự kiện cho nút "Bỏ qua"
        buttonSkip.setOnClickListener {
            Toast.makeText(this, "Bạn đã bỏ qua câu hỏi này", Toast.LENGTH_SHORT).show()
            // Chuyển sang activity tiếp theo nếu có
            goToNextActivity()
        }

        // Xử lý sự kiện cho các nút mức độ
        buttonBeginner.setOnClickListener {
            selectedAnswer = buttonBeginner.text.toString()
            Toast.makeText(this, "Bạn đã chọn: $selectedAnswer", Toast.LENGTH_SHORT).show()
        }

        buttonIntermediate.setOnClickListener {
            selectedAnswer = buttonIntermediate.text.toString()
            Toast.makeText(this, "Bạn đã chọn: $selectedAnswer", Toast.LENGTH_SHORT).show()
        }

        buttonAdvanced.setOnClickListener {
            selectedAnswer = buttonAdvanced.text.toString()
            Toast.makeText(this, "Bạn đã chọn: $selectedAnswer", Toast.LENGTH_SHORT).show()
        }

        // Xử lý sự kiện cho nút "Tiếp theo"
        btnNext.setOnClickListener {
            if (selectedAnswer != null) {
                Toast.makeText(this, "Bạn đã chọn: $selectedAnswer", Toast.LENGTH_SHORT).show()
                goToNextActivity()
            } else {
                Toast.makeText(this, "Vui lòng chọn một mức độ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm để chuyển sang activity tiếp theo nếu cần
    private fun goToNextActivity() {
        val intent = Intent(this, Gender::class.java)
        startActivity(intent)
    }
}
