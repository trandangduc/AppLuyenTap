package com.example.appluyentap

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class homepage : AppCompatActivity() {
    private var currentExerciseIndex = 0  // Biến theo dõi bài tập hiện tại

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val exercises = listOf(
            Exercise("Bật nhảy", "00:20", R.raw.ic_jump),
            Exercise("Tập cơ bụng", "x16", R.raw.ic_crunch),
            Exercise("Gập bụng chéo kiểu Nga", "x20", R.raw.ic_russian_twist),
            Exercise("Leo núi", "x16", R.raw.ic_mountain_climber),
            Exercise("Chạm gót chân", "x20", R.raw.ic_heel_touch),
            Exercise("Nâng chân", "x16", R.raw.ic_leg_raise), // Thêm bài tập nâng chân
            Exercise("Đo sàn", "00:20", R.raw.ic_plank), // Thêm bài tập đo sàn 00:20

        )


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ExerciseAdapter(exercises)

        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            // Kiểm tra nếu vẫn còn bài tập để thực hiện
            if (currentExerciseIndex < exercises.size) {
                val currentExercise = exercises[currentExerciseIndex]

                // Thực hiện hành động (ở đây chỉ hiển thị tên bài tập)
                Toast.makeText(this, "Bắt đầu: ${currentExercise.name}", Toast.LENGTH_SHORT).show()

                // Tăng chỉ số bài tập lên để lần nhấn kế tiếp sẽ lấy bài tập tiếp theo
                currentExerciseIndex++
            } else {
                // Nếu đã hoàn thành tất cả các bài tập, thông báo và đặt lại index để lặp lại từ đầu
                Toast.makeText(this, "Hoàn thành tất cả bài tập!", Toast.LENGTH_SHORT).show()
                currentExerciseIndex = 0
            }
        }
    }
}
