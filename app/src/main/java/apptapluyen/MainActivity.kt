package com.example.apptapluyen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Thiết lập lắng nghe sự kiện click cho các nút
        val button1 = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)

        // Chuyển qua trang Ít vận động
        button1.setOnClickListener {
            val intent = Intent(this, ActivityItVanDong::class.java)
            startActivity(intent)
        }

        // Chuyển qua trang Hơi tích cực
        button2.setOnClickListener {
            val intent = Intent(this, ActivityHoiTichCuc::class.java)
            startActivity(intent)
        }

        // Chuyển qua trang Tích cực vừa phải
        button3.setOnClickListener {
            val intent = Intent(this, ActivityTichCucVuaPhai::class.java)
            startActivity(intent)
        }

        // Chuyển qua trang Rất tích cực
        button4.setOnClickListener {
            val intent = Intent(this, ActivityRatTichCuc::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
