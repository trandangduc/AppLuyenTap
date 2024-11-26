package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import data.ListKhamPha

class explore_itemFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BaiTapAdapter
    private val listKhamPha = mutableListOf<ListKhamPha>()

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("BaiTapKhamPha")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore_item, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BaiTapAdapter(listKhamPha) { item ->
            onItemClick(item)
        }
        recyclerView.adapter = adapter

        // Load data from Firebase
        loadFirebaseData()

        return view
    }

    private fun loadFirebaseData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listKhamPha.clear()
                for (data in snapshot.children) {
                    val khamPha = data.getValue(ListKhamPha::class.java)
                    if (khamPha != null) {
                        listKhamPha.add(khamPha)
                    }
                }
                // Log danh sách sau khi đã tải xong
                for (item in listKhamPha) {
                    Log.d("TAG", "Loaded Item: $item")
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun onItemClick(item: ListKhamPha) {


    }



}
