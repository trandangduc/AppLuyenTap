package com.example.appluyentap

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import data.Category
import data.ListKhamPha

class CategoryAdapter(private val categoryList: List<Category>, private val context: Context) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private lateinit var recyclerView: ListView
    private lateinit var adapter: BaiTapAdapter
    private val listKhamPha = mutableListOf<ListKhamPha>()
    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("BaiTapKhamPha")
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_explore_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Lấy tên danh mục từ danh sách và gán vào TextView
        val category = categoryList[position]
        holder.categoryName.text = category.Ten
        recyclerView = holder.itemView.findViewById(R.id.listView)

        adapter = BaiTapAdapter(listKhamPha) { item ->
            onItemClick(item)
        }
        recyclerView.adapter = adapter

        // Load data from Firebase
        loadFirebaseData(category,recyclerView)

    }
    private fun setListViewHeightBasedOnItems(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = 0
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(
                View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.UNSPECIFIED
            )
            totalHeight += listItem.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count))
        listView.layoutParams = params
        listView.requestLayout()
    }
    private fun loadFirebaseData(category: Category, recyclerView: ListView) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listKhamPha.clear()
                for (data in snapshot.children) {
                    val khamPha = data.getValue(ListKhamPha::class.java)
                    if (khamPha != null && khamPha.ID == category.ID) {
                        listKhamPha.add(khamPha)
                    }
                }
                // Log danh sách sau khi đã tải xong
                for (item in listKhamPha) {
                    Log.d("TAG", "Loaded Item: $item")
                }
                adapter.notifyDataSetChanged()
                setListViewHeightBasedOnItems(recyclerView)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun onItemClick(item: ListKhamPha) {
        // Tạo Intent để gửi dữ liệu đến Activity khác
        val intent = Intent(context, ListExercise::class.java).apply {
            Log.d("KhamPha","ID: ${item.IDKhamPha}")
            putExtra("KhamPha", item.IDKhamPha) // Gửi ID của ListKhamPha
        }

        // Bắt đầu Activity
        context.startActivity(intent)
    }
    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.category_name)
    }
}
