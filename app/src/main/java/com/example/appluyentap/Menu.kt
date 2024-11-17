package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu : AppCompatActivity() {

    private var selectedTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu) // Đảm bảo tên tệp XML là `activity_menu.xml`

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        loadFragment(BeginnerFragment())

        val beginnerTab = findViewById<TextView>(R.id.beginner_tab)
        val intermediateTab = findViewById<TextView>(R.id.intermediate_tab)
        val advancedTab = findViewById<TextView>(R.id.advanced_tab)

        beginnerTab.setOnClickListener {
            setSelectedTextView(beginnerTab)
            loadFragment(BeginnerFragment())
            Toast.makeText(this, "Hiển thị bài tập cho người bắt đầu", Toast.LENGTH_SHORT).show()
        }

        intermediateTab.setOnClickListener {
            setSelectedTextView(intermediateTab)
            loadFragment(IntermediateFragment())
            Toast.makeText(this, "Hiển thị bài tập cho trình độ trung bình", Toast.LENGTH_SHORT).show()
        }

        advancedTab.setOnClickListener {
            setSelectedTextView(advancedTab)
            loadFragment(AdvancedFragment())
            Toast.makeText(this, "Hiển thị bài tập cho trình độ nâng cao", Toast.LENGTH_SHORT).show()
        }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.workout -> {
                    Toast.makeText(this, "Tập luyện", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.explore -> {
                    Toast.makeText(this, "Khám phá", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.report -> {
                    Toast.makeText(this, "Báo cáo", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    Toast.makeText(this, "Cài đặt", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun setSelectedTextView(selected: TextView) {
        selectedTextView?.setTextColor(resources.getColor(R.color.colorBlack)) // Màu mặc định
        selected.setTextColor(resources.getColor(R.color.colorPrimaryDark)) // Màu khi được chọn
        selectedTextView = selected
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment) // Đảm bảo `main_container` là ID của FrameLayout trong XML
            .commit()
    }
    // Hàm để chuyển sang homepage khi nhấn nút "Tiếp theo"
    private fun goToNextActivity() {
        val intent = Intent(this, homepage::class.java)
        startActivity(intent)
    }
}
