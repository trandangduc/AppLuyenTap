package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Giúp app sử dụng toàn bộ không gian màn hình
        setContentView(R.layout.activity_profile)

        // Lắng nghe sự kiện thay đổi màn hình (thêm padding cho phần hệ thống)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lấy thông tin người dùng từ Firebase
        val user = FirebaseAuth.getInstance().currentUser
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val heightTextView = findViewById<TextView>(R.id.heightTextView)
        val weightTextView = findViewById<TextView>(R.id.weightTextView)
        val ageTextView = findViewById<TextView>(R.id.ageTextView)
        val goalTextView = findViewById<TextView>(R.id.goalTextView)
        val editProfileButton: Button = findViewById(R.id.editProfileButton)
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        if (user != null) {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance()
            val userRef = database.reference.child("users").child(userId)

            userRef.get().addOnSuccessListener {
                if (it.exists()) {
                    // Lấy dữ liệu từ Firebase và cập nhật vào các TextView
                    val height = it.child("height").getValue(Int::class.java)
                    val weight = it.child("weight").getValue(Int::class.java)
                    val age = it.child("age").getValue(Int::class.java)
                    val goal = it.child("goal").getValue(String::class.java)

                    nameTextView.text = user.displayName ?: "Tên người dùng"
                    heightTextView.text = "Chiều cao: ${height ?: "Chưa cập nhật"} cm"
                    weightTextView.text = "Cân nặng: ${weight ?: "Chưa cập nhật"} kg"
                    ageTextView.text = "Tuổi: ${age ?: "Chưa cập nhật"}"
                    goalTextView.text = "Mục tiêu: ${goal ?: "Chưa cập nhật"}"
                }
            }
        }
    }
}
