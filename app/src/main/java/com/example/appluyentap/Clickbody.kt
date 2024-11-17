package com.example.appluyentap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Clickbody : AppCompatActivity() {

    private lateinit var buttonToanThan: Button
    private lateinit var buttonCanhTay: Button
    private lateinit var buttonNguc: Button
    private lateinit var buttonBung: Button
    private lateinit var buttonChan: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonSkip: Button

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

        // Khởi tạo các nút
        buttonToanThan = findViewById(R.id.buttonToanThan)
        buttonCanhTay = findViewById(R.id.buttonCanhTay)
        buttonNguc = findViewById(R.id.buttonNguc)
        buttonBung = findViewById(R.id.buttonBung)
        buttonChan = findViewById(R.id.buttonChan)
        buttonNext = findViewById(R.id.button25) // Nút "Tiếp theo"
        buttonSkip = findViewById(R.id.buttonNext) // Nút "Bỏ qua"
        val imageback = findViewById<ImageButton>(R.id.Targetback)

        // Thêm sự kiện cho nút Toàn thân
        buttonToanThan.setOnClickListener {
            setButtonColors(Color.BLUE, Color.WHITE)
        }

        // Thêm sự kiện cho nút "Back"
        imageback.setOnClickListener {
            // Quay lại trang trước (trang Gender)
            onBackPressed() // Lệnh này tự động quay lại trang trước
        }

        // Thêm sự kiện cho các nút khác
        buttonCanhTay.setOnClickListener {
            setSingleButtonColor(buttonCanhTay)
        }

        buttonNguc.setOnClickListener {
            setSingleButtonColor(buttonNguc)
        }

        buttonBung.setOnClickListener {
            setSingleButtonColor(buttonBung)
        }

        buttonChan.setOnClickListener {
            setSingleButtonColor(buttonChan)
        }

        // Thêm sự kiện cho nút Tiếp theo
        buttonNext.setOnClickListener {
            // Chuyển đến trang Impulse
            val intent = Intent(this, Impulse::class.java)
            startActivity(intent)
        }

        // Thêm sự kiện cho nút Bỏ qua
        buttonSkip.setOnClickListener {
            // Chuyển đến trang Impulse
            val intent = Intent(this, Impulse::class.java)
            startActivity(intent)
        }
    }

    private fun setButtonColors(backgroundColor: Int, textColor: Int) {
        val buttons = arrayOf(buttonToanThan, buttonCanhTay, buttonNguc, buttonBung, buttonChan)
        for (button in buttons) {
            button.setBackgroundColor(backgroundColor)
            button.setTextColor(textColor)
        }
    }

    private fun setSingleButtonColor(selectedButton: Button) {
        // Đặt màu nền cho nút đã chọn
        selectedButton.setBackgroundColor(Color.BLUE) // Màu nền nút được chọn
        selectedButton.setTextColor(Color.WHITE) // Màu chữ của nút được chọn

        // Đặt màu cho các nút khác về mặc định
        val buttons = arrayOf(buttonToanThan, buttonCanhTay, buttonNguc, buttonBung, buttonChan)
        for (button in buttons) {
            if (button != selectedButton) {
                button.setBackgroundColor(Color.WHITE) // Màu nền mặc định
                button.setTextColor(Color.BLACK) // Màu chữ mặc định
            }
        }
    }
}
