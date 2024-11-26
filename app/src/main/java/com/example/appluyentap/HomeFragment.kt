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

        beginnerTab.setOnClickListener {
            val beginnerFragment = BeginnerFragment()
            parentFragmentManager.beginTransaction()
                .replace(fragmentManager.id, beginnerFragment) // Sử dụng fragmentContainer.id thay cho R.id.fragment_container
                .commit()
            Toast.makeText(requireContext(), "Người bắt đầu được chọn", Toast.LENGTH_SHORT).show()
        }

        intermediateTab.setOnClickListener {
            val beginnerFragment = IntermediateFragment()
            parentFragmentManager.beginTransaction()
                .replace(fragmentManager.id, beginnerFragment) // Sử dụng fragmentContainer.id thay cho R.id.fragment_container
                .commit()
            Toast.makeText(requireContext(), "Trung bình được chọn", Toast.LENGTH_SHORT).show()
        }

        advancedTab.setOnClickListener {
            val beginnerFragment = AdvancedFragment()
            parentFragmentManager.beginTransaction()
                .replace(fragmentManager.id, beginnerFragment) // Sử dụng fragmentContainer.id thay cho R.id.fragment_container
                .commit()
            Toast.makeText(requireContext(), "Nâng cao được chọn", Toast.LENGTH_SHORT).show()
        }

        // Gọi fragment khác
        val beginnerFragment = BeginnerFragment()
        parentFragmentManager.beginTransaction()
            .replace(fragmentManager.id, beginnerFragment) // Sử dụng fragmentContainer.id thay cho R.id.fragment_container
            .commit()




    }
}
