package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Color

class Gender : AppCompatActivity() {

    private lateinit var imgMale: Button
    private lateinit var imgFemale: Button
    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button

    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        // Liên kết các thành phần từ XML
        imgMale = findViewById(R.id.buttonNam)
        imgFemale = findViewById(R.id.buttonNu)
        btnNext = findViewById(R.id.button25)
        btnSkip = findViewById(R.id.buttonNext)

        // Lắng nghe WindowInsets để điều chỉnh padding cho hệ thống thanh bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Xử lý khi người dùng chọn Nam
        imgMale.setOnClickListener {
            selectedGender = "Nam"
            imgMale.setBackgroundColor(Color.parseColor("#FFDDDDFF")) // Màu nền khi chọn Nam
            imgFemale.setBackgroundColor(Color.parseColor("#FFFFFFFF")) // Trở lại màu mặc định cho Nữ
        }

        // Xử lý khi người dùng chọn Nữ
        imgFemale.setOnClickListener {
            selectedGender = "Nữ"
            imgFemale.setBackgroundColor(Color.parseColor("#FFFFDDDD")) // Màu nền khi chọn Nữ
            imgMale.setBackgroundColor(Color.parseColor("#FFFFFFFF")) // Trở lại màu mặc định cho Nam
        }

        // Xử lý khi người dùng nhấn nút Tiếp theo
        btnNext.setOnClickListener {
            if (selectedGender != null) {
                Toast.makeText(this, "Bạn đã chọn: $selectedGender", Toast.LENGTH_SHORT).show()
                // Chuyển đến activity hoặc hành động tiếp theo
                startActivity(Intent(this, Impulse::class.java))
            } else {
                Toast.makeText(this, "Vui lòng chọn một giới tính", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý khi người dùng nhấn nút Bỏ qua
        btnSkip.setOnClickListener {
            // Xử lý hành động bỏ qua
            startActivity(Intent(this, Clickbody::class.java))
        }


    }
}
