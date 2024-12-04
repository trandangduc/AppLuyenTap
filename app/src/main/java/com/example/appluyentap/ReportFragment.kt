package com.example.appluyentap

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
class ReportFragment : Fragment() {

    private lateinit var tvTotalWorkouts: TextView
    private lateinit var tvMonthlyWorkouts: TextView
    private lateinit var tvTotalWorkoutTime: TextView
    private lateinit var pieChartBMI: PieChart

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
        pieChartBMI = view.findViewById(R.id.pieChartBMI)

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
                // Cập nhật PieChart
                updatePieChart(bmi)


            }
        }

        return view
    }
    private fun updatePieChart(bmi: Double) {
        val entries = mutableListOf<PieEntry>()
        pieChartBMI.setUsePercentValues(false)
        when {
            bmi < 18.5 -> {
                entries.add(PieEntry(bmi.toFloat(), "Thiếu cân"))
            }
            bmi in 18.5..24.9 -> {
                entries.add(PieEntry(bmi.toFloat(), "Bình thường"))
            }
            bmi in 25.0..29.9 -> {
                entries.add(PieEntry(bmi.toFloat(), "Thừa cân"))
            }
            else -> {
                entries.add(PieEntry(bmi.toFloat(), "Béo phì"))
            }
        }

        val dataSet = PieDataSet(entries, "Chỉ số BMI")
        dataSet.colors = listOf(
            Color.parseColor("#FF7043"), // Thiếu cân
            Color.parseColor("#66BB6A"), // Bình thường
            Color.parseColor("#FFA726"), // Thừa cân
            Color.parseColor("#EF5350")  // Béo phì
        )
        dataSet.valueTextSize = 20f

        val pieData = PieData(dataSet)
        pieData.setValueTextSize(18f)
        pieChartBMI.data = pieData
        pieChartBMI.invalidate() // Làm mới biểu đồ
        pieChartBMI.description.text = "Phân loại BMI"
        pieChartBMI.setEntryLabelColor(Color.BLACK)
        pieChartBMI.setUsePercentValues(false)
    }
}

