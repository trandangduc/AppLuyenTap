package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Goal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        // Lấy các view
        val heightSeekBar = findViewById<SeekBar>(R.id.heightSeekBar)
        val heightValue = findViewById<TextView>(R.id.heightValue)

        val weightSeekBar = findViewById<SeekBar>(R.id.weightSeekBar)
        val weightValue = findViewById<TextView>(R.id.weightValue)

        val ageInput = findViewById<EditText>(R.id.ageInput)
        val goalSpinner = findViewById<Spinner>(R.id.goalSpinner)

        val nextButton = findViewById<Button>(R.id.button25)

        // Lắng nghe sự thay đổi của SeekBar cho chiều cao
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

        // Firebase Database reference
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.reference


        // Sự kiện khi nhấn nút "Tiếp theo"
        nextButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val height = heightSeekBar.progress
            val weight = weightSeekBar.progress
            val age = ageInput.text.toString().toIntOrNull() ?: 0 // Nếu không nhập thì default là 0
            val goal = goalSpinner.selectedItem.toString()

            // Lưu dữ liệu vào Firebase
            if (user != null) {
                val userId = user.uid // Thay đổi ID người dùng thực tế của bạn ở đây
                val userRef = myRef.child("users").child(userId)
                userRef.child("height").setValue(height)
                userRef.child("weight").setValue(weight)
                userRef.child("age").setValue(age)
                userRef.child("goal").setValue(goal)
                startActivity(Intent(this, Menu::class.java))
            }
        }
    }
}
