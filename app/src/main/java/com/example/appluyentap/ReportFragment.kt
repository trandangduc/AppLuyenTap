package com.example.appluyentap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
class ReportFragment : Fragment() {

    private lateinit var tvTotalWorkouts: TextView
    private lateinit var tvMonthlyWorkouts: TextView
    private lateinit var tvTotalWorkoutTime: TextView
    private lateinit var tvBMIValue: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_report, container, false)

        // Ánh xạ các View
        tvTotalWorkouts = view.findViewById(R.id.tvTotalWorkouts)
        tvMonthlyWorkouts = view.findViewById(R.id.tvMonthlyWorkouts)
        tvTotalWorkoutTime = view.findViewById(R.id.tvTotalWorkoutTime)
        tvBMIValue = view.findViewById(R.id.tvBMIValue)

        // Lấy thông tin người dùng từ Firebase
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = it.uid
            val database = FirebaseDatabase.getInstance().reference
            val userRef = database.child("users").child(userId)

            // Lấy dữ liệu người dùng từ Firebase và cập nhật giao diện
            userRef.get().addOnSuccessListener { dataSnapshot ->
                val totalWorkouts = dataSnapshot.child("totalWorkouts").getValue(Int::class.java) ?: 0
                val monthlyWorkouts = dataSnapshot.child("monthlyWorkouts").getValue(Int::class.java) ?: 0
                val totalWorkoutTime = dataSnapshot.child("totalWorkoutTime").getValue(Int::class.java) ?: 0
                val weight = dataSnapshot.child("weight").getValue(Double::class.java) ?: 0.0
                val height = dataSnapshot.child("height").getValue(Double::class.java) ?: 0.0

                // Tính toán BMI (BMI = weight / height^2)
                val bmi = if (height > 0) weight / (height * height / 10000) else 0.0

                // Cập nhật các TextView
                tvTotalWorkouts.text = "Tổng số lần tập: $totalWorkouts"
                tvMonthlyWorkouts.text = "Số lần tập trong tháng: $monthlyWorkouts"
                tvTotalWorkoutTime.text = "Tổng thời gian tập luyện: ${totalWorkoutTime} phút"

                // Cập nhật chỉ số BMI
                tvBMIValue.text = "Chỉ số BMI: %.2f".format(bmi)

                // Tính toán giá trị tiến trình BMI
                val bmiPercentage = when {
                    bmi < 18.5 -> 20
                    bmi in 18.5..24.9 -> ((bmi - 18.5) / (24.9 - 18.5) * 80).toInt() + 20
                    bmi in 25.0..29.9 -> ((bmi - 25.0) / (29.9 - 25.0) * 50).toInt() + 80
                    else -> 100
                }

            }
        }

        return view
    }
}

