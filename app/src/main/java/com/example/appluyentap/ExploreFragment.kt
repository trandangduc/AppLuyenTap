package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import data.Category

class ExploreFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryList: MutableList<Category>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        // Firebase reference
        val database = FirebaseDatabase.getInstance()
        val categoryRef = database.getReference("DanhMucKhamPha")

        categoryList = mutableListOf()

        // Lắng nghe dữ liệu từ Firebase
        categoryRef.get().addOnSuccessListener { dataSnapshot ->
            // Lấy dữ liệu từ Firebase
            for (categorySnapshot in dataSnapshot.children) {
                val categoryId = categorySnapshot.child("ID").getValue(Int::class.java) // Lấy ID
                val categoryName = categorySnapshot.child("Ten").getValue(String::class.java) // Lấy Ten

                // Kiểm tra nếu cả ID và Ten đều không null
                if (categoryId != null && categoryName != null) {
                    // Thêm vào danh sách
                    categoryList.add(Category(categoryId, categoryName))
                    Log.d("Category", "Category added: ID = $categoryId, Name = $categoryName")
                }
            }

            // Cập nhật RecyclerView
            categoryAdapter = CategoryAdapter(categoryList,requireContext())
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = categoryAdapter
            val chestLayout = view.findViewById<LinearLayout>(R.id.chestLayout)
            val armLayout = view.findViewById<LinearLayout>(R.id.CanhtayvaVaiLayout)
            val legLayout = view.findViewById<LinearLayout>(R.id.mongvachanLayout)
            val absLayout = view.findViewById<LinearLayout>(R.id.saumuiLayout)

            // Xử lý sự kiện click
            chestLayout.setOnClickListener {
                val intent = Intent(requireContext(), ListExercise::class.java)
                intent.putExtra("exerciseType", "Nguc") // Truyền dữ liệu loại bài tập
                startActivity(intent)
            }

            armLayout.setOnClickListener {
                val intent = Intent(requireContext(), ListExercise::class.java)
                intent.putExtra("exerciseType", "CanhTayVaVai")
                startActivity(intent)
            }

            legLayout.setOnClickListener {
                val intent = Intent(requireContext(), ListExercise::class.java)
                intent.putExtra("exerciseType", "MongVaChan")
                startActivity(intent)
            }

            absLayout.setOnClickListener {
                val intent = Intent(requireContext(), ListExercise::class.java)
                intent.putExtra("exerciseType", "Bung")
                startActivity(intent)
            }

        }

        return view
    }
}

