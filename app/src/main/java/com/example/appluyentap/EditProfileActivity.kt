package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfileActivity : AppCompatActivity() {

    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        // Lắng nghe sự kiện thay đổi màn hình (thêm padding cho phần hệ thống)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lấy thông tin người dùng từ Firebase
        val user = FirebaseAuth.getInstance().currentUser
        val heightTextView = findViewById<TextView>(R.id.heightValue)
        val weightTextView = findViewById<TextView>(R.id.weightValue)
        val ageTextView = findViewById<EditText>(R.id.ageInput)
        val goalSpinner = findViewById<Spinner>(R.id.goalSpinner)
        // Lấy các view
        val heightSeekBar = findViewById<SeekBar>(R.id.heightSeekBar)
        val heightValue = findViewById<TextView>(R.id.heightValue)
        val weightSeekBar = findViewById<SeekBar>(R.id.weightSeekBar)
        val weightValue = findViewById<TextView>(R.id.weightValue)

        heightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Cập nhật giá trị chiều cao vào TextView
                heightValue.text = "$progress cm"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Lắng nghe sự thay đổi của SeekBar cho cân nặng
        weightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Cập nhật giá trị cân nặng vào TextView
                weightValue.text = "$progress kg"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        // Khi người dùng đăng nhập, lấy thông tin từ Firebase
        user?.let {
            val userId = it.uid
            val database = FirebaseDatabase.getInstance()
            userRef = database.reference.child("users").child(userId)

            // Lấy dữ liệu từ Firebase và cập nhật vào UI
            userRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val height = snapshot.child("height").getValue(Int::class.java)
                    val weight = snapshot.child("weight").getValue(Int::class.java)
                    val age = snapshot.child("age").getValue(Int::class.java)
                    val goal = snapshot.child("goal").getValue(String::class.java)

                    heightTextView.text = "Chiều cao: ${height ?: "Chưa cập nhật"} cm"
                    weightTextView.text = "Cân nặng: ${weight ?: "Chưa cập nhật"} kg"
                    ageTextView.setText(age.toString())
                    // Nếu có mục tiêu trong Firebase, chọn trong Spinner
                    val adapter = goalSpinner.adapter
                    for (i in 0 until adapter.count) {
                        if (adapter.getItem(i).toString() == goal) {
                            goalSpinner.setSelection(i)
                            break
                        }
                    }
                }
            }
        }

        // Lưu thông tin khi nhấn nút "Lưu"
        val saveProfileButton: Button = findViewById(R.id.saveProfileButton)
        saveProfileButton.setOnClickListener {
            val height = heightSeekBar.progress
            val weight = weightSeekBar.progress
            val age = ageTextView.text.toString().toIntOrNull() ?: 0 // Nếu không nhập thì default là 0
            val goal = goalSpinner.selectedItem.toString()

            // Lưu dữ liệu vào Firebase
            if (user != null) {
                userRef.child("height").setValue(height)
                userRef.child("weight").setValue(weight)
                userRef.child("age").setValue(age)
                userRef.child("goal").setValue(goal)
                startActivity(Intent(this, Menu::class.java))
            }
        }

    }
}