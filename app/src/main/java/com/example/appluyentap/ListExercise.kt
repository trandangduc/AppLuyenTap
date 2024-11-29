package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import data.Exercise
import data.ExerciseKhamPha
import data.Initiate
import data.ListKhamPha
import data.Stretching
import java.io.Serializable

class ListExercise : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val mainExercises = mutableListOf<Exercise>()
    private val stretchingExercises = mutableListOf<Stretching>()
    private val warmupExercises = mutableListOf<Initiate>()
    private val exercisesList = mutableListOf<ExerciseKhamPha>()
    private lateinit var bophankhampha : String
    private var khampha : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_exercise)
        val boPhanCondition = intent.getStringExtra("BoPhan") ?: ""
        val mucDoCondition = intent.getStringExtra("MucDo") ?: ""
        bophankhampha = intent.getStringExtra("exerciseType") ?: ""
        khampha = intent.getIntExtra("KhamPha",0)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            if (khampha == 0) {
                // Tạo Intent để chuyển sang ExerciseActivity
                val intent = Intent(this, ExerciseActivity::class.java)

                // Truyền ba danh sách bài tập
                intent.putExtra("mainExercises", mainExercises as Serializable)
                intent.putExtra("stretchingExercises", stretchingExercises as Serializable)
                intent.putExtra("warmupExercises", warmupExercises as Serializable)
                // Truyền thời gian bắt đầu
                val startTime = System.currentTimeMillis() // Lấy thời gian hiện tại (timestamp)
                intent.putExtra("startTime", startTime)

                startActivity(intent)
            }
            else {
                if (exercisesList.isNotEmpty()) {
                    // Tạo Intent để chuyển sang ExerciseActivity
                    val intent = Intent(this, ExerciseActivity::class.java)
                    Log.d("Exercises", "Fetched Exercises: ${exercisesList.size}")
                    // Truyền danh sách bài tập
                    intent.putExtra("Exercises", exercisesList as Serializable)

                    // Truyền thời gian bắt đầu
                    val startTime = System.currentTimeMillis() // Lấy thời gian hiện tại (timestamp)
                    intent.putExtra("startTime", startTime)

                    startActivity(intent)
                } else {
                    // Hiển thị thông báo hoặc xử lý khi dữ liệu chưa sẵn sàng
                    Toast.makeText(this, "Dữ liệu chưa tải xong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val backbtn : ImageButton = findViewById(R.id.backButton)
        backbtn.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
        if (bophankhampha != "") {
            exerciseAdapter = ExerciseAdapter(this,mainExercises, stretchingExercises, warmupExercises)
            recyclerView.adapter = exerciseAdapter
            fetchExercisesKhamPha()
        }
        else if (khampha != 0) {
            fetchBaiTapKhamPhaData(khampha)
        }
        else {
            exerciseAdapter = ExerciseAdapter(this,mainExercises, stretchingExercises, warmupExercises)
            recyclerView.adapter = exerciseAdapter
            fetchExercises(boPhanCondition, mucDoCondition)
        }
    }
    fun fetchBaiTapKhamPhaData(khamphaId: Int) {
        // Tạo reference tới nhánh BaiTapKhamPha trong Firebase
        val baiTapKhamPhaRef = FirebaseDatabase.getInstance().getReference("BaiTapKhamPha/$khamphaId")

        // Lấy dữ liệu từ Firebase
        baiTapKhamPhaRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val baiTapKhamPha = task.result.getValue(ListKhamPha::class.java)

                if (baiTapKhamPha != null) {
                    // Xử lý danh sách bài tập một cách bất đồng bộ

                    val totalExercises = baiTapKhamPha.ListBaiTapKhamPha?.size ?: 0
                    var fetchedCount = 0

                    baiTapKhamPha.ListBaiTapKhamPha?.forEach { id ->
                        Log.d("fetchExerciseFromId", "Fetching exercise with ID: $id")
                        fetchExerciseFromId(id) { exercise ->
                            if (exercise != null) {
                                exercisesList.add(exercise)
                                Log.d("exercisesList", "Added Exercise: ${exercise.TenBaiTap}, ID: ${exercise.ID}")
                            } else {
                                Log.d("exercisesList", "No exercise found for ID: $id")
                            }

                            // Kiểm tra nếu đã lấy xong tất cả bài tập
                            fetchedCount++
                            if (fetchedCount == totalExercises) {
                                // Log danh sách các bài tập sau khi đã xử lý xong
                                Log.d("exercisesList", "Fetched Exercises: ${exercisesList.size}")
                                exercisesList.forEach { exercise ->
                                    Log.d("exercisesList", "Exercise ID: ${exercise.ID}, Name: ${exercise.TenBaiTap}")
                                }

                                // Gọi hàm để hiển thị danh sách
                                displayExercises(exercisesList)
                            }
                        }
                    }
                } else {
                    Log.d("exercisesList", "baiTapKhamPha is null")
                }

            } else {
                // Xử lý lỗi nếu không lấy được dữ liệu
                Log.e("Firebase", "Error getting data", task.exception)
            }
        }
    }


    // Hàm để hiển thị dữ liệu vào RecyclerView
    fun displayExercises(exercises: List<ExerciseKhamPha>) {
        val adapter = BaiTapKhamPhaAdapter(this, exercises)
        recyclerView.adapter = adapter
    }
    fun fetchExerciseFromId(id: Int, callback: (ExerciseKhamPha?) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference("ListBaiTapKhamPha/$id")
        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val exercise = task.result.getValue(ExerciseKhamPha::class.java)
                Log.d("fetchExerciseFromId", "Fetched Exercise: $exercise")
                callback(exercise) // Gọi callback khi đã có dữ liệu
            } else {
                Log.e("fetchExerciseFromId", "Failed to fetch exercise: ${task.exception?.message}")
                callback(null)
            }
        }
    }




    private fun fetchExercisesKhamPha() {
        val coTheTapTrungRef = FirebaseDatabase.getInstance().getReference("CoTheTapTrung/${bophankhampha}")

        // Lấy bài tập chính từ CoTheTapTrung/BoPhan/BaiTapChinh
        fetchExerciseIdsFromBranch(coTheTapTrungRef.child("BaiTapChinh")) { baiTapIds ->
            Log.d("fetchExercisesKhamPha", "BaiTapChinh IDs: ${baiTapIds.joinToString()}")
            if (baiTapIds.isEmpty()) {
                Toast.makeText(this@ListExercise, "Không có bài tập nào trong BaiTapChinh!", Toast.LENGTH_SHORT).show()
            } else {
                val baiTapRef = FirebaseDatabase.getInstance().getReference("BaiTap/BaiTapChinh")
                fetchExercisesFromBranch(baiTapRef, baiTapIds, ::handleMainExercise)
            }
        }

        // Lấy bài tập giãn cơ từ CoTheTapTrung/BoPhan/GianCo
        fetchExerciseIdsFromBranch(coTheTapTrungRef.child("GianCo")) { baiTapIds ->
            Log.d("fetchExercisesKhamPha", "GianCo IDs: ${baiTapIds.joinToString()}")
            if (baiTapIds.isEmpty()) {
                Toast.makeText(this@ListExercise, "Không có bài tập nào trong GianCo!", Toast.LENGTH_SHORT).show()
            } else {
                val baiTapRef = FirebaseDatabase.getInstance().getReference("BaiTap/GianCo")
                fetchExercisesFromBranch(baiTapRef, baiTapIds, ::handleStretchingExercise)
            }
        }

        // Lấy bài tập khởi động từ CoTheTapTrung/BoPhan/KhoiDong
        fetchExerciseIdsFromBranch(coTheTapTrungRef.child("KhoiDong")) { baiTapIds ->
            Log.d("fetchExercisesKhamPha", "KhoiDong IDs: ${baiTapIds.joinToString()}")
            if (baiTapIds.isEmpty()) {
                Toast.makeText(this@ListExercise, "Không có bài tập nào trong KhoiDong!", Toast.LENGTH_SHORT).show()
            } else {
                val baiTapRef = FirebaseDatabase.getInstance().getReference("BaiTap/KhoiDong")
                fetchExercisesFromBranch(baiTapRef, baiTapIds, ::handleWarmupExercise)
            }
        }
    }

    // Hàm lấy danh sách ID từ một nhánh
    private fun fetchExerciseIdsFromBranch(branchRef: DatabaseReference, handleExerciseIds: (List<Int>) -> Unit) {
        branchRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val baiTapIds = mutableListOf<Int>()

                // Duyệt qua tất cả các phần tử con trong snapshot
                for (exerciseSnapshot in snapshot.children) {
                    val id = exerciseSnapshot.value?.toString()?.toIntOrNull()  // Chuyển value thành Int
                    id?.let { baiTapIds.add(it) }  // Nếu có ID hợp lệ, thêm vào danh sách
                }

                // Gọi callback với danh sách các ID bài tập
                handleExerciseIds(baiTapIds)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error nếu cần
                Toast.makeText(this@ListExercise, "Lỗi tải dữ liệu!", Toast.LENGTH_SHORT).show()
            }
        })
    }


    // Hàm chung để fetch dữ liệu từ một nhánh bài tập
    private fun fetchExercisesFromBranch(
        branchRef: DatabaseReference,
        baiTapIds: List<Int>,
        handleExercise: (DataSnapshot) -> Unit
    ) {
        branchRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (id in baiTapIds) {
                    val exerciseSnapshot = snapshot.child(id.toString())
                    if (exerciseSnapshot.exists()) {
                        handleExercise(exerciseSnapshot)
                    }
                }
                exerciseAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListExercise, "Lỗi tải dữ liệu từ ${branchRef.key}!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Hàm xử lý bài tập chính
    private fun handleMainExercise(exerciseSnapshot: DataSnapshot) {
        val boPhan = exerciseSnapshot.child("BoPhan").value.toString()
        val mucDo = exerciseSnapshot.child("MucDo").value.toString()
        val soRep = exerciseSnapshot.child("SoRep").value.toString().toInt()
        val tenBaiTap = exerciseSnapshot.child("TenBaiTap").value.toString()
        val video = exerciseSnapshot.child("Video").value?.toString() ?: ""
        val id1 = exerciseSnapshot.child("ID").value.toString().toInt()
        mainExercises.add(Exercise(id1, boPhan, mucDo, soRep, tenBaiTap, video))
    }

    // Hàm xử lý bài tập giãn cơ
    private fun handleStretchingExercise(exerciseSnapshot: DataSnapshot) {
        val boPhan = exerciseSnapshot.child("BoPhan").value.toString()
        val thoiGian = exerciseSnapshot.child("ThoiGian").value.toString().toInt()
        val tenBaiTap = exerciseSnapshot.child("TenBaiTap").value.toString()
        val video = exerciseSnapshot.child("Video").value?.toString() ?: ""
        val id1 = exerciseSnapshot.child("ID").value.toString().toInt()
        stretchingExercises.add(Stretching(boPhan, id1, tenBaiTap, thoiGian, video))
    }

    // Hàm xử lý bài tập khởi động
    private fun handleWarmupExercise(exerciseSnapshot: DataSnapshot) {
        val boPhan = exerciseSnapshot.child("BoPhan").value.toString()
        val thoiGian = exerciseSnapshot.child("ThoiGian").value.toString().toInt()
        val tenBaiTap = exerciseSnapshot.child("TenBaiTap").value.toString()
        val video = exerciseSnapshot.child("Video").value?.toString() ?: ""
        val id1 = exerciseSnapshot.child("ID").value.toString().toInt()
        warmupExercises.add(Initiate(boPhan, id1, tenBaiTap, thoiGian, video))
    }



    private fun fetchExercises(boPhanCondition: String, mucDoCondition: String) {

        val baiTapRef = FirebaseDatabase.getInstance().getReference("BaiTap")

        // Fetch BaiTapChinh (Exercise)
        baiTapRef.child("BaiTapChinh").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (exerciseSnapshot in snapshot.children) {

                    val mucDo = exerciseSnapshot.child("MucDo").value?.toString() ?: ""
                    val bophan = exerciseSnapshot.child("BoPhan").value?.toString() ?: ""

                    // Kiểm tra xem mức độ có phù hợp với điều kiện không
                    if (mucDo == mucDoCondition && bophan == boPhanCondition) {
                        // Nếu mức độ phù hợp, thêm bài tập vào danh sách
                        addMainExerciseIfMatches(exerciseSnapshot, boPhanCondition, mucDoCondition)
                        // Log dữ liệu bài tập để kiểm tra

                    }
                }
                exerciseAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListExercise, "Lỗi tải BaiTapChinh!", Toast.LENGTH_SHORT).show()
            }
        })

        // Fetch GianCo (Stretching)
        baiTapRef.child("GianCo").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (exerciseSnapshot in snapshot.children) {

                    // Log dữ liệu mỗi lần thêm một bài tập
                    // Lấy Mức độ từ Firebase
                    val bophan = exerciseSnapshot.child("BoPhan").value?.toString() ?: ""

                    // Kiểm tra xem mức độ có phù hợp với điều kiện không

                        // Nếu mức độ phù hợp, thêm bài tập vào danh sách
                        addStretchingExerciseIfMatches(exerciseSnapshot)
                        // Log dữ liệu bài tập để kiểm tra


                }
                exerciseAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListExercise, "Lỗi tải GianCo!", Toast.LENGTH_SHORT).show()
            }
        })

        // Fetch KhoiDong (Initiate)
        baiTapRef.child("KhoiDong").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (exerciseSnapshot in snapshot.children) {

                    val bophan = exerciseSnapshot.child("BoPhan").value?.toString() ?: ""

                    // Kiểm tra xem mức độ có phù hợp với điều kiện không

                        addInitiateExerciseIfMatches(exerciseSnapshot)

                }
                exerciseAdapter.notifyDataSetChanged()

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
            Log.d("ExerciseAdapter", "stretchingExercises: ${mainExercises.joinToString(", ") { it.toString() }}")

        }
    }

    private fun addStretchingExerciseIfMatches(
        snapshot: DataSnapshot
    ) {
        val boPhan = snapshot.child("BoPhan").value.toString()
        val thoiGian = snapshot.child("ThoiGian").value.toString().toInt()
        val tenBaiTap = snapshot.child("TenBaiTap").value.toString()
        val video = snapshot.child("Video").value?.toString() ?: ""
        val id = snapshot.child("ID").value.toString().toInt()
            stretchingExercises.add(Stretching(boPhan,id, tenBaiTap, thoiGian, video))
            // Log toàn bộ danh sách stretchingExercises
            Log.d("ExerciseAdapter", "stretchingExercises: ${stretchingExercises.joinToString(", ") { it.toString() }}")

    }

    private fun addInitiateExerciseIfMatches(
        snapshot: DataSnapshot
    ) {
        val boPhan = snapshot.child("BoPhan").value.toString()

        val thoiGian = snapshot.child("ThoiGian").value.toString().toInt()
        val tenBaiTap = snapshot.child("TenBaiTap").value.toString()
        val video = snapshot.child("Video").value?.toString() ?: ""
        val id = snapshot.child("ID").value.toString().toInt()


        warmupExercises.add(Initiate(boPhan,id,tenBaiTap,thoiGian, video))
        Log.d("ExerciseAdapter", "stretchingExercises: ${warmupExercises.joinToString(", ") { it.toString() }}")
    }
}
