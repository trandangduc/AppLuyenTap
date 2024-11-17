package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Levelofmovement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_levelofmovement)

        // Thiết lập lắng nghe sự kiện click cho các nút
        val button1 = findViewById<Button>(R.id.buttonItVanDong)
        val button2 = findViewById<Button>(R.id.buttonHoiTichCuc)
        val button3 = findViewById<Button>(R.id.buttonTichCucVuaPhai)
        val button4 = findViewById<Button>(R.id.buttonRatTichCuc)
        val imageBack = findViewById<ImageButton>(R.id.Targetback)  // ImageButton để quay lại trang trước

        // Chuyển qua trang Ít vận động
        button1.setOnClickListener {
            val intent = Intent(this, Targetpage::class.java)
            startActivity(intent)
        }

        // Chuyển qua trang Hơi tích cực
        button2.setOnClickListener {
            val intent = Intent(this, Targetpage::class.java)
            startActivity(intent)
        }

        // Chuyển qua trang Tích cực vừa phải
        button3.setOnClickListener {
            val intent = Intent(this, Targetpage::class.java)
            startActivity(intent)
        }

        // Chuyển qua trang Rất tích cực
        button4.setOnClickListener {
            val intent = Intent(this, Targetpage::class.java)
            startActivity(intent)
        }

        // Thêm sự kiện click cho ImageButton (quay lại trang trước)
        imageBack.setOnClickListener {
            onBackPressed() // Quay lại trang trước
        }

        // Đảm bảo padding cho hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
