package com.example.appluyentap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Measure : AppCompatActivity() {
    private lateinit var buttonBack: ImageButton
    private lateinit var buttonSkip: Button
    private lateinit var btnNext: Button
    private lateinit var buttonBeginner: Button
    private lateinit var buttonIntermediate: Button
    private lateinit var buttonAdvanced: Button

    // Biến lưu trữ câu trả lời đã chọn
    private var selectedAnswer: String? = null

    // Biến trạng thái cho các nút mức độ
    private val buttonStates = BooleanArray(3) { false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        // Liên kết các thành phần từ XML
        buttonBack = findViewById(R.id.imageButton)         // ID của ImageButton
        buttonSkip = findViewById(R.id.button21)            // ID của nút "Bỏ qua"
        btnNext = findViewById(R.id.button25)               // ID của nút "Tiếp theo"
        buttonBeginner = findViewById(R.id.button22)        // ID của nút "Người bắt đầu"
        buttonIntermediate = findViewById(R.id.button23)    // ID của nút "Trung bình"
        buttonAdvanced = findViewById(R.id.button24)        // ID của nút "Nâng cao"

        // Xử lý sự kiện cho nút "Quay trở về"
        buttonBack.setOnClickListener {
            val intent = Intent(this, Gender::class.java)
            startActivity(intent)
            finish()
        }

        // Xử lý sự kiện cho nút "Bỏ qua"
        buttonSkip.setOnClickListener {
            Toast.makeText(this, "Bạn đã bỏ qua câu hỏi này", Toast.LENGTH_SHORT).show()
            goToImpulseActivity()
        }

        // Xử lý sự kiện cho các nút mức độ bằng cách sử dụng toggleButtonState
        buttonBeginner.setOnClickListener {
            toggleButtonState(0, buttonBeginner, buttonBeginner.text.toString())
        }

        buttonIntermediate.setOnClickListener {
            toggleButtonState(1, buttonIntermediate, buttonIntermediate.text.toString())
        }

        buttonAdvanced.setOnClickListener {
            toggleButtonState(2, buttonAdvanced, buttonAdvanced.text.toString())
        }

        // Xử lý sự kiện cho nút "Tiếp theo"
        btnNext.setOnClickListener {
            if (selectedAnswer != null) {
                Toast.makeText(this, "Bạn đã chọn: $selectedAnswer", Toast.LENGTH_SHORT).show()
                goToNextActivity()
                val intent = Intent(this@Measure, Menu::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Vui lòng chọn một mức độ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm đổi màu nút khi click
    private fun toggleButtonState(index: Int, selectedButton: Button, answer: String) {
        // Đặt lại màu cho tất cả các nút
        resetButtonColors()

        // Cập nhật trạng thái của nút được chọn
        buttonStates[index] = !buttonStates[index] // Đảo ngược trạng thái

        // Cập nhật màu sắc của nút dựa trên trạng thái
        if (buttonStates[index]) {
            selectedButton.setBackgroundColor(Color.parseColor("#007BFF")) // Màu nền khi nút được chọn
            selectedButton.setTextColor(Color.WHITE) // Đổi màu văn bản thành trắng khi chọn
            selectedAnswer = answer
            Toast.makeText(this, "Bạn đã chọn: $answer", Toast.LENGTH_SHORT).show()
        } else {
            selectedButton.setBackgroundColor(Color.WHITE) // Trả về màu nền trắng
            selectedButton.setTextColor(Color.BLACK) // Đổi màu văn bản về đen khi bỏ chọn
            selectedAnswer = null
        }
    }

    // Hàm khôi phục màu cho tất cả các nút
    private fun resetButtonColors() {
        buttonBeginner.setBackgroundColor(Color.WHITE)
        buttonBeginner.setTextColor(Color.BLACK)
        buttonIntermediate.setBackgroundColor(Color.WHITE)
        buttonIntermediate.setTextColor(Color.BLACK)
        buttonAdvanced.setBackgroundColor(Color.WHITE)
        buttonAdvanced.setTextColor(Color.BLACK)
    }

    // Hàm để chuyển sang trang Impulse khi nhấn nút "Bỏ qua"
    private fun goToImpulseActivity() {
        val intent = Intent(this, Impulse::class.java)
        startActivity(intent)
    }

    // Hàm để chuyển sang homepage khi nhấn nút "Tiếp theo"
    private fun goToNextActivity() {
        val intent = Intent(this, homepage::class.java)
        startActivity(intent)
    }
}
