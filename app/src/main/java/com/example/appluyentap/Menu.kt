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
        loadFragment(HomeFragment())


        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.workout -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.explore -> {
                    loadFragment(ExploreFragment())
                    true
                }
                R.id.report -> {
                    loadFragment(ReportFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(SettingFragment())
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
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)

        // Thêm vào back stack nếu muốn quay lại
        transaction.addToBackStack(null)

        transaction.commit()
    }


}
