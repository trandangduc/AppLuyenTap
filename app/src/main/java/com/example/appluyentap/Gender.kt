package com.example.appluyentap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Gender : AppCompatActivity() {

    private lateinit var imgMale: Button
    private lateinit var imgFemale: Button
    private lateinit var btnNext: Button

    private var selectedGender: String? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        // Liên kết các thành phần từ XML
        imgMale = findViewById(R.id.buttonNam)
        imgFemale = findViewById(R.id.buttonNu)
        btnNext = findViewById(R.id.button25)

        // Khởi tạo tham chiếu đến Firebase Realtime Database
        database = Firebase.database.reference

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
                saveGenderToDatabase(selectedGender!!)
            } else {
                Toast.makeText(this, "Vui lòng chọn một giới tính", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveGenderToDatabase(gender: String) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userId = user.uid

            // Lưu giới tính vào Realtime Database
            database.child("users").child(userId).child("gender").setValue(gender)
                .addOnSuccessListener {
                    Toast.makeText(this, "Lưu giới tính thành công!", Toast.LENGTH_SHORT).show()
                    // Chuyển đến trang tiếp theo
                    startActivity(Intent(this, Goal::class.java))
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Lỗi khi lưu giới tính: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show()
        }
    }
}
