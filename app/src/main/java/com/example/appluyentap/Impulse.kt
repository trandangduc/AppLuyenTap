package com.example.appluyentap

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Impulse : AppCompatActivity() {

    private lateinit var btnConfidence: Button
    private lateinit var btnRelax: Button
    private lateinit var btnHealth: Button
    private lateinit var btnEnergy: Button
    private lateinit var btnNext: Button

    // Danh sách trạng thái nút
    private val buttonStates = BooleanArray(4) // Sử dụng mảng để theo dõi trạng thái của các nút

    private var selectedAnswer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

        // Đặt màu nền mặc định cho các nút
        setDefaultButtonColors()

        // Set up các listener cho các Button để lưu lựa chọn
        btnConfidence.setOnClickListener {
            toggleButtonState(0, btnConfidence, "Cảm thấy tự tin")
        }

        btnRelax.setOnClickListener {
            toggleButtonState(1, btnRelax, "Giải tỏa căng thẳng")
        }

        btnHealth.setOnClickListener {
            toggleButtonState(2, btnHealth, "Cải thiện sức khỏe")
        }

        btnEnergy.setOnClickListener {
            toggleButtonState(3, btnEnergy, "Tăng cường năng lượng")
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

    // Hàm để chọn nút và đổi màu
    private fun toggleButtonState(index: Int, selectedButton: Button, answer: String) {
        // Đặt lại màu cho tất cả các nút
        resetButtonColors()

        // Thay đổi trạng thái
        buttonStates[index] = !buttonStates[index]

        // Cập nhật màu sắc của nút
        if (buttonStates[index]) {
            selectedButton.setBackgroundColor(Color.parseColor("#FF5722")) // Màu khi nút được chọn
            selectedAnswer = answer
            showToast("Bạn đã chọn: $answer")
        } else {
            selectedButton.setBackgroundColor(Color.WHITE) // Trả về màu trắng
            selectedAnswer = null
        }
    }

    // Hàm khôi phục màu cho tất cả các nút
    private fun resetButtonColors() {
        btnConfidence.setBackgroundColor(Color.WHITE)
        btnRelax.setBackgroundColor(Color.WHITE)
        btnHealth.setBackgroundColor(Color.WHITE)
        btnEnergy.setBackgroundColor(Color.WHITE)
    }

    // Hàm đặt màu nền mặc định cho các nút
    private fun setDefaultButtonColors() {
        btnConfidence.setBackgroundColor(Color.WHITE)
        btnRelax.setBackgroundColor(Color.WHITE)
        btnHealth.setBackgroundColor(Color.WHITE)
        btnEnergy.setBackgroundColor(Color.WHITE)
    }

    // Hàm hiển thị Toast thông báo
    private fun showToast(message: String) {
        Toast.makeText(this@Impulse, message, Toast.LENGTH_SHORT).show()
    }
}
