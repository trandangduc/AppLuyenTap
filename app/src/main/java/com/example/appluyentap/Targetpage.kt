package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Targetpage : AppCompatActivity() {

    // Biến để lưu trữ ngày được chọn
    private val selectedDays = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_targetpage)

        // Xử lý sự kiện của các nút ngày
        val buttons = arrayOf(
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7)
        )

        // Đặt sự kiện click cho các nút
        for (i in buttons.indices) {
            val button = buttons[i]
            button.setOnClickListener {
                toggleDaySelection(i + 1, button)
            }
        }

        // Sự kiện khi nhấn nút "Tiếp theo"
        findViewById<Button>(R.id.button25).setOnClickListener {
            // Kiểm tra số lượng ngày đã chọn
            if (selectedDays.size >= 3) {
                // Tiến hành chuyển trang hoặc thực hiện hành động tiếp theo
                Toast.makeText(this, "Đã chọn đủ 3 ngày!", Toast.LENGTH_SHORT).show()

                // Chuyển sang trang Menu (hoặc trang khác)
                val intent = Intent(this,Menu::class.java) // Thay "MenuActivity" bằng tên Activity của bạn
                startActivity(intent)
            } else {
                // Hiển thị thông báo nếu chưa chọn đủ 3 ngày
                Toast.makeText(this, "Vui lòng chọn ít nhất 3 ngày", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm để thêm hoặc xóa ngày khỏi danh sách được chọn và thay đổi màu sắc nút
    private fun toggleDaySelection(day: Int, button: Button) {
        if (selectedDays.contains(day)) {
            selectedDays.remove(day)  // Nếu đã chọn thì bỏ chọn
            button.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light)) // Đổi màu nút về màu gốc
        } else {
            if (selectedDays.size < 7) {
                selectedDays.add(day)     // Nếu chưa chọn thì thêm vào danh sách
                button.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light)) // Đổi màu nút khi chọn
            }
        }
    }
}
