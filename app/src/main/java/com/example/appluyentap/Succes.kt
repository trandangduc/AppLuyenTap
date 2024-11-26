package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Succes : AppCompatActivity() {

    private lateinit var tvCongratulation: TextView
    private lateinit var tvWorkoutTime: TextView
    private lateinit var ivConfetti: ImageView
    private lateinit var btnBack: Button  // Nút Trở về

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succes)

        // Ánh xạ các View
        tvCongratulation = findViewById(R.id.tvCongratulation)
        tvWorkoutTime = findViewById(R.id.tvWorkoutTime)
        ivConfetti = findViewById(R.id.ivConfetti)
        btnBack = findViewById(R.id.btnBack)  // Ánh xạ nút trở về

        // Nhận thời gian từ Intent
        val startTime = intent.getLongExtra("startTime", 0L)
        Log.d("startTime", "startTime: $startTime")

        val endTime = System.currentTimeMillis()
        Log.d("endTime", "endTime: $endTime")
        val workoutTimeInMillis = endTime - startTime

        // Tính toán thời gian tập luyện
        val workoutTimeInMinutes = (workoutTimeInMillis / 1000) / 60

        // Cập nhật TextView hiển thị thời gian tập luyện
        tvWorkoutTime.text = "Thời gian tập: $workoutTimeInMinutes phút"

        // Áp dụng hiệu ứng fade-in cho phần chúc mừng
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        tvCongratulation.startAnimation(fadeIn)

        // Hiệu ứng confetti nếu có
        ivConfetti.startAnimation(fadeIn)

        // Lưu thông tin vào Firebase
        saveWorkoutData(workoutTimeInMinutes)

        // Xử lý sự kiện khi nhấn nút "Trở về"
        btnBack.setOnClickListener {
            // Quay lại Activity Menu hoặc Activity khác
            val intent = Intent(this, Menu::class.java)  // Thay "MenuActivity" bằng tên Activity mà bạn muốn quay lại
            startActivity(intent)
            finish()  // Kết thúc Activity hiện tại
        }
    }

    private fun saveWorkoutData(workoutTimeInMinutes: Long) {
        // Lấy thông tin người dùng hiện tại từ Firebase Authentication
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            // Lấy tham chiếu tới Realtime Database
            val database = FirebaseDatabase.getInstance().getReference("users/$userId")

            // Lấy thông tin hiện tại của người dùng
            database.get().addOnSuccessListener { snapshot ->
                // Lấy các giá trị hiện tại từ Firebase
                val currentWorkoutCount = snapshot.child("totalWorkouts").getValue(Int::class.java) ?: 0
                val currentWorkoutTime = snapshot.child("totalWorkoutTime").getValue(Int::class.java) ?: 0
                val currentMonthlyWorkouts = snapshot.child("monthlyWorkouts").getValue(Int::class.java) ?: 0
                val currentLastWorkoutDate = snapshot.child("lastWorkoutDate").getValue(String::class.java) ?: ""

                // Lấy ngày tháng hiện tại
                val currentMonth = SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(Date())

                // Kiểm tra xem ngày tập luyện gần nhất có phải tháng trước không
                if (currentLastWorkoutDate != currentMonth) {
                    // Nếu là tháng trước, reset số lần tập trong tháng
                    val updatedMonthlyWorkouts = 1 // Bắt đầu lại từ 1
                    val updatedLastWorkoutDate = currentMonth // Cập nhật ngày tập luyện gần nhất
                    val updatedWorkoutCount = currentWorkoutCount + 1
                    val updatedWorkoutTime = currentWorkoutTime + workoutTimeInMinutes

                    // Lưu lại dữ liệu vào Firebase
                    val userUpdates = hashMapOf<String, Any>(
                        "totalWorkoutTime" to updatedWorkoutTime,
                        "totalWorkouts" to updatedWorkoutCount,
                        "monthlyWorkouts" to updatedMonthlyWorkouts,
                        "lastWorkoutDate" to updatedLastWorkoutDate
                    )
                    database.updateChildren(userUpdates)
                } else {
                    // Nếu là cùng tháng, tăng số lần tập trong tháng
                    val updatedMonthlyWorkouts = currentMonthlyWorkouts + 1
                    val updatedLastWorkoutDate = currentMonth // Cập nhật ngày tập luyện gần nhất
                    val updatedWorkoutCount = currentWorkoutCount + 1
                    val updatedWorkoutTime = currentWorkoutTime + workoutTimeInMinutes

                    // Lưu lại dữ liệu vào Firebase
                    val userUpdates = hashMapOf<String, Any>(
                        "totalWorkoutTime" to updatedWorkoutTime,
                        "totalWorkouts" to updatedWorkoutCount,
                        "monthlyWorkouts" to updatedMonthlyWorkouts,
                        "lastWorkoutDate" to updatedLastWorkoutDate
                    )
                    database.updateChildren(userUpdates)
                }
            }
        }
    }

}
