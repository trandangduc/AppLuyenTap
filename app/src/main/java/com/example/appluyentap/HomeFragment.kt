package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.Exercise

class HomeFragment : Fragment() {
    private var currentExerciseIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle tab clicks for "Người bắt đầu", "Trung bình", and "Nâng cao"
        val beginnerTab: TextView = view.findViewById(R.id.beginner_tab)
        val intermediateTab: TextView = view.findViewById(R.id.intermediate_tab)
        val advancedTab: TextView = view.findViewById(R.id.advanced_tab)
        val fragmentManager : FragmentContainerView = view.findViewById(R.id.fragment_container)
        val tabs = listOf(beginnerTab, intermediateTab, advancedTab)

        // Hàm reset màu mặc định cho tất cả các tab
        fun resetTabStyles() {
            tabs.forEach { tab ->
                tab.setBackgroundResource(R.drawable.tab_background) // Background mặc định
                tab.setTextColor(resources.getColor(R.color.tabTextColor)) // Màu chữ mặc định
            }
        }

        // Xử lý sự kiện khi click vào các tab
        beginnerTab.setOnClickListener {
            resetTabStyles()
            selectTab(beginnerTab)
            parentFragmentManager.beginTransaction()
                .replace(fragmentManager.id, BeginnerFragment())
                .commit()

        }

        intermediateTab.setOnClickListener {
            resetTabStyles()
            selectTab(intermediateTab)
            parentFragmentManager.beginTransaction()
                .replace(fragmentManager.id, IntermediateFragment())
                .commit()

        }

        advancedTab.setOnClickListener {
            resetTabStyles()
            selectTab(advancedTab)
            parentFragmentManager.beginTransaction()
                .replace(fragmentManager.id, AdvancedFragment())
                .commit()

        }

        // Gọi mặc định "Người bắt đầu"
        resetTabStyles()
        selectTab(beginnerTab)
        parentFragmentManager.beginTransaction()
            .replace(fragmentManager.id, BeginnerFragment())
            .commit()
    }
    private fun selectTab(tab: TextView) {
        tab.setBackgroundResource(R.drawable.tab_selector) // Background được chọn
        tab.setTextColor(resources.getColor(android.R.color.black)) // Màu chữ trắng
    }
}
