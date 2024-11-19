package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_setting, container, false)

        // Áp dụng WindowInsets để điều chỉnh các thanh trạng thái và điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Thiết lập sự kiện click cho các LinearLayout
        rootView.findViewById<LinearLayout>(R.id.layoutWorkoutSetting).setOnClickListener {
            val intent = Intent(requireContext(), WorkoutSetting::class.java)
            startActivity(intent)
        }

        rootView.findViewById<LinearLayout>(R.id.layoutGeneralSetting).setOnClickListener {
            val intent = Intent(requireContext(), GeneralSetting::class.java)
            startActivity(intent)
        }

        rootView.findViewById<LinearLayout>(R.id.layoutTTSSetting).setOnClickListener {
            val intent = Intent(requireContext(), FeedBack::class.java)
            startActivity(intent)
        }

        rootView.findViewById<LinearLayout>(R.id.layoutAssess).setOnClickListener {
            val intent = Intent(requireContext(), Assess::class.java)
            startActivity(intent)
        }

        return rootView
    }
}
