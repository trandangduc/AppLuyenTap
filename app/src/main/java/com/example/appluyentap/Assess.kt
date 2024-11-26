package com.example.appluyentap

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Assess : AppCompatActivity() {
    private lateinit var ratingBar: RatingBar
    private lateinit var submitButton: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assess)

        ratingBar = findViewById(R.id.ratingBar)
        submitButton = findViewById(R.id.submitButton)

        // Khởi tạo Firebase Auth và Realtime Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        submitButton.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid // Lấy ID người dùng hiện tại
                val rating = ratingBar.rating

                if (rating > 0) {
                    // Kiểm tra xem người dùng đã đánh giá trước đó chưa
                    val userRatingRef = database.child("ratings").child(userId)
                    userRatingRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                // Người dùng đã đánh giá trước đó, cập nhật đánh giá
                                userRatingRef.child("rating").setValue(rating)
                                userRatingRef.child("timestamp").setValue(System.currentTimeMillis())
                                    .addOnSuccessListener {
                                        Toast.makeText(this@Assess, "Đánh giá đã được cập nhật!", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this@Assess, "Có lỗi xảy ra: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                // Người dùng chưa đánh giá, thêm đánh giá mới
                                val ratingData = Rating(rating, System.currentTimeMillis())
                                userRatingRef.setValue(ratingData)
                                    .addOnSuccessListener {
                                        Toast.makeText(this@Assess, "Đánh giá đã được gửi!", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this@Assess, "Có lỗi xảy ra: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@Assess, "Có lỗi xảy ra: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this, "Vui lòng cho điểm đánh giá", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Định nghĩa dữ liệu đánh giá
    data class Rating(val rating: Float, val timestamp: Long)
}
