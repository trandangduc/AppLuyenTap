package com.example.appluyentap

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Impulse : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_impulse)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.radioGroup)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        radioGroup = findViewById(R.id.radioGroup)
        btnNext = findViewById(R.id.btn_next)


        btnNext.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedId)

            if (selectedRadioButton != null) {
                val answer = selectedRadioButton.text.toString()
                Toast.makeText(this@Impulse, "Bạn đã chọn: $answer", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this@Impulse, "Vui lòng chọn một tùy chọn", Toast.LENGTH_SHORT).show()
            }
        }
    }
}