package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import data.ListKhamPha

class explore_itemFragment : Fragment() {

    private lateinit var listView: ListView
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

        // Set up the adapter for the ListView
        listView = view.findViewById(R.id.listView)
        adapter = BaiTapAdapter(listKhamPha) { item ->
            onItemClick(item)
        }
        listView.adapter = adapter

        setListViewHeightBasedOnItems(listView)

        // Load data from Firebase
        loadFirebaseData()

        return view
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
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1))
        listView.layoutParams = params
        listView.requestLayout()
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
                setListViewHeightBasedOnItems(listView)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun onItemClick(item: ListKhamPha) {


    }



}
