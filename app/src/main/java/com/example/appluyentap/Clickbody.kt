package com.example.apptapluyen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appluyentap.R

class Clickbody : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_clickbody)

        // Thiết lập lắng nghe sự kiện click cho các nút
        val button1 = findViewById<Button>(R.id.btntoanthan)
        val button2 = findViewById<Button>(R.id.btncanhtay)
        val button3 = findViewById<Button>(R.id.btnnguc)
        val button4 = findViewById<Button>(R.id.btnbung)

        // Xử lý sự kiện click cho các nút
        button1.setOnClickListener {
            val intent = Intent(this, ToanthanActivity::class.java)
            startActivity(intent)
            // Xử lý cho button 1
        }

        button2.setOnClickListener {
            // Xử lý cho button 2
        }

        button3.setOnClickListener {
            // Xử lý cho button 3
        }

        button4.setOnClickListener {
            // Xử lý cho button 4
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
