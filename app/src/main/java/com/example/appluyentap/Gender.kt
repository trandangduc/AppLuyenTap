package com.example.appluyentap

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Gender : AppCompatActivity() {

    private lateinit var imgMale: ImageView
    private lateinit var imgFemale: ImageView
    private lateinit var btnNext: TextView

    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        // Liên kết các thành phần từ XML
        imgMale = findViewById(R.id.img_male)
        imgFemale = findViewById(R.id.img_female)
        btnNext = findViewById(R.id.btn_next)

        // Lắng nghe WindowInsets để điều chỉnh padding cho hệ thống thanh bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Xử lý khi người dùng chọn Nam
        imgMale.setOnClickListener {
            selectedGender = "Nam"
            imgMale.isSelected = true
            imgFemale.isSelected = false
        }

        // Xử lý khi người dùng chọn Nữ
        imgFemale.setOnClickListener {
            selectedGender = "Nữ"
            imgFemale.isSelected = true
            imgMale.isSelected = false
        }

        // Xử lý khi người dùng nhấn nút Tiếp theo
        btnNext.setOnClickListener {
            if (selectedGender != null) {
                Toast.makeText(this, "Bạn đã chọn: $selectedGender", Toast.LENGTH_SHORT).show()
                // Chuyển đến activity hoặc hành động tiếp theo
                // startActivity(Intent(this, NextActivity::class.java))
            } else {
                Toast.makeText(this, "Vui lòng chọn một giới tính", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
