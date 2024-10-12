package com.example.apptapluyen

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityItVanDong : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itvandong) // Đảm bảo rằng tên layout là chính xác

        // Tìm nút trở lại và thiết lập lắng nghe sự kiện click
        val buttonBack = findViewById<Button>(R.id.btnback1) // Đảm bảo ID này tồn tại trong layout
        buttonBack.setOnClickListener {
            finish() // Quay lại trang trước đó (MainActivity)
        }

        // Bạn có thể thêm các thành phần UI khác hoặc logic tại đây
    }
}
