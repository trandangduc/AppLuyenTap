package com.example.appluyentap

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Impulse : AppCompatActivity() {

    private lateinit var btnConfidence: Button
    private lateinit var btnRelax: Button
    private lateinit var btnHealth: Button
    private lateinit var btnEnergy: Button
    private lateinit var btnNext: Button

    private var selectedAnswer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_impulse)

        // Áp dụng WindowInsets để hỗ trợ cử chỉ hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_confidence)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo các Button
        btnConfidence = findViewById(R.id.btn_confidence)
        btnRelax = findViewById(R.id.btn_relax)
        btnHealth = findViewById(R.id.btn_health)
        btnEnergy = findViewById(R.id.btn_energy)
        btnNext = findViewById(R.id.btn_next)

        // Set up các listener cho các Button để lưu lựa chọn
        btnConfidence.setOnClickListener {
            selectedAnswer = "Cảm thấy tự tin"
            showToast("Bạn đã chọn: Cảm thấy tự tin")
        }

        btnRelax.setOnClickListener {
            selectedAnswer = "Giải tỏa căng thẳng"
            showToast("Bạn đã chọn: Giải tỏa căng thẳng")
        }

        btnHealth.setOnClickListener {
            selectedAnswer = "Cải thiện sức khỏe"
            showToast("Bạn đã chọn: Cải thiện sức khỏe")
        }

        btnEnergy.setOnClickListener {
            selectedAnswer = "Tăng cường năng lượng"
            showToast("Bạn đã chọn: Tăng cường năng lượng")
        }

        // Listener cho nút Tiếp theo
        btnNext.setOnClickListener {
            if (selectedAnswer != null) {
                Toast.makeText(this@Impulse, "Bạn đã chọn: $selectedAnswer", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@Impulse, "Vui lòng chọn một tùy chọn", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm hiển thị Toast thông báo
    private fun showToast(message: String) {
        Toast.makeText(this@Impulse, message, Toast.LENGTH_SHORT).show()
    }
}
