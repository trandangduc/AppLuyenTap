package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import data.Exercise
import data.Initiate
import data.Stretching

class ListExercise : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter

    private val mainExercises = mutableListOf<Exercise>()
    private val stretchingExercises = mutableListOf<Stretching>()
    private val warmupExercises = mutableListOf<Initiate>()
    private var currentExerciseIndex = 0
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_exercise)

        val boPhanCondition = intent.getStringExtra("BoPhan") ?: ""
        val mucDoCondition = intent.getStringExtra("MucDo") ?: ""
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        exerciseAdapter = ExerciseAdapter(mainExercises, stretchingExercises, warmupExercises)
        recyclerView.adapter = exerciseAdapter

        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            if (currentExerciseIndex < mainExercises.size) {
                val currentExercise = mainExercises[currentExerciseIndex]

                val intent = Intent(this, ExerciseActivity::class.java)
                //intent.putExtra("exercise", currentExercise)
                startActivity(intent)

                currentExerciseIndex++
            } else {
                Toast.makeText(this, "Hoàn thành tất cả bài tập!", Toast.LENGTH_SHORT).show()
                currentExerciseIndex = 0
            }
        }

        fetchExercises(boPhanCondition, mucDoCondition)
    }

    private fun fetchExercises(boPhanCondition: String, mucDoCondition: String) {

        val baiTapRef = FirebaseDatabase.getInstance().getReference("BaiTap")

        // Fetch BaiTapChinh (Exercise)
        baiTapRef.child("BaiTapChinh").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (exerciseSnapshot in snapshot.children) {
                    loadingProgressBar.visibility = View.VISIBLE
                    val mucDo = exerciseSnapshot.child("MucDo").value?.toString() ?: ""
                    val bophan = exerciseSnapshot.child("BoPhan").value?.toString() ?: ""

                    // Kiểm tra xem mức độ có phù hợp với điều kiện không
                    if (mucDo == mucDoCondition && bophan == boPhanCondition) {
                        // Nếu mức độ phù hợp, thêm bài tập vào danh sách
                        addMainExerciseIfMatches(exerciseSnapshot, boPhanCondition, mucDoCondition)
                        // Log dữ liệu bài tập để kiểm tra
                        Log.d("FirebaseData", "Dữ liệu bài tập: ID = ${exerciseSnapshot.child("ID").value}, Tên bài tập = ${exerciseSnapshot.child("TenBaiTap").value}, Bộ phận = ${exerciseSnapshot.child("BoPhan").value}, Mức độ = $mucDo")
                    }
                }
                exerciseAdapter.notifyDataSetChanged()
                loadingProgressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListExercise, "Lỗi tải BaiTapChinh!", Toast.LENGTH_SHORT).show()
            }
        })

        // Fetch GianCo (Stretching)
        baiTapRef.child("GianCo").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (exerciseSnapshot in snapshot.children) {
                    loadingProgressBar.visibility = View.VISIBLE
                    // Log dữ liệu mỗi lần thêm một bài tập
                    // Lấy Mức độ từ Firebase
                    val bophan = exerciseSnapshot.child("BoPhan").value?.toString() ?: ""

                    // Kiểm tra xem mức độ có phù hợp với điều kiện không
                    if (bophan == boPhanCondition) {
                        // Nếu mức độ phù hợp, thêm bài tập vào danh sách
                        addStretchingExerciseIfMatches(exerciseSnapshot, boPhanCondition)
                        // Log dữ liệu bài tập để kiểm tra
                        Log.d("FirebaseData", "Dữ liệu bài tập: ID = ${exerciseSnapshot.child("ID").value}, Tên bài tập = ${exerciseSnapshot.child("TenBaiTap").value}, Bộ phận = ${exerciseSnapshot.child("BoPhan").value}")
                    }
                }
                exerciseAdapter.notifyDataSetChanged()
                loadingProgressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListExercise, "Lỗi tải GianCo!", Toast.LENGTH_SHORT).show()
            }
        })

        // Fetch KhoiDong (Initiate)
        baiTapRef.child("KhoiDong").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (exerciseSnapshot in snapshot.children) {
                    loadingProgressBar.visibility = View.VISIBLE
                    val bophan = exerciseSnapshot.child("BoPhan").value?.toString() ?: ""

                    // Kiểm tra xem mức độ có phù hợp với điều kiện không
                    if (bophan == boPhanCondition) {
                        addInitiateExerciseIfMatches(exerciseSnapshot, boPhanCondition)
                    }
                }
                exerciseAdapter.notifyDataSetChanged()
                loadingProgressBar.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListExercise, "Lỗi tải KhoiDong!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addMainExerciseIfMatches(
        snapshot: DataSnapshot,
        boPhanCondition: String,
        mucDoCondition: String
    ) {
        val boPhan = snapshot.child("BoPhan").value.toString()
        val mucDo = snapshot.child("MucDo").value.toString()
        val soRep = snapshot.child("SoRep").value.toString().toInt()
        val tenBaiTap = snapshot.child("TenBaiTap").value.toString()
        val video = snapshot.child("Video").value?.toString() ?: ""
        val id = snapshot.child("ID").value.toString().toInt()

        if (boPhan == boPhanCondition && mucDo == mucDoCondition) {


            mainExercises.add(Exercise(id, boPhan, mucDo, soRep, tenBaiTap, video))
        }
    }

    private fun addStretchingExerciseIfMatches(
        snapshot: DataSnapshot,
        boPhanCondition: String
    ) {
        val boPhan = snapshot.child("BoPhan").value.toString()
        val thoiGian = snapshot.child("ThoiGian").value.toString().toInt()
        val tenBaiTap = snapshot.child("TenBaiTap").value.toString()
        val video = snapshot.child("Video").value?.toString() ?: ""
        val id = snapshot.child("ID").value.toString().toInt()

        if (boPhan == boPhanCondition ) {
            stretchingExercises.add(Stretching(boPhan,id, tenBaiTap, thoiGian, video))
        }
    }

    private fun addInitiateExerciseIfMatches(
        snapshot: DataSnapshot,
        boPhanCondition: String,
    ) {
        val boPhan = snapshot.child("BoPhan").value.toString()

        val thoiGian = snapshot.child("ThoiGian").value.toString().toInt()
        val tenBaiTap = snapshot.child("TenBaiTap").value.toString()
        val video = snapshot.child("Video").value?.toString() ?: ""
        val id = snapshot.child("ID").value.toString().toInt()

        if (boPhan == boPhanCondition) {
            warmupExercises.add(Initiate(boPhan,id,tenBaiTap,thoiGian, video))
        }
    }
}
