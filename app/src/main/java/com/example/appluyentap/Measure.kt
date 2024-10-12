package com.example.appluyentap

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MeasureActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_measure)

        // Xử lý sự kiện khi hệ thống cài đặt các thông số
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.radioGroup)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Liên kết các thành phần từ XML
        radioGroup = findViewById(R.id.radioGroup)
        btnNext = findViewById(R.id.btn_next)

        // Xử lý sự kiện khi nhấn nút "Tiếp theo"
        btnNext.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedId)

            if (selectedRadioButton != null) {
                val answer = selectedRadioButton.text.toString()
                Toast.makeText(this@MeasureActivity, "Bạn đã chọn: $answer", Toast.LENGTH_SHORT).show()

                // Thêm hành động tiếp theo ở đây, như chuyển sang activity tiếp theo
            } else {
                Toast.makeText(this@MeasureActivity, "Vui lòng chọn một mức độ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
