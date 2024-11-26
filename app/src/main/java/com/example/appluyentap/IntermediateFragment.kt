package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class IntermediateFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intermediate, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gán sự kiện nhấn cho từng card
        view.findViewById<LinearLayout>(R.id.title1).setOnClickListener {
            // Tạo Intent để mở ListExercise
            val intent = Intent(requireContext(), ListExercise::class.java)

            // Truyền thêm dữ liệu thông qua Intent
            intent.putExtra("BoPhan", "Bụng") // Giá trị "Bung" cho bộ phận
            intent.putExtra("MucDo", "Trung Bình")   // Giá trị "De" cho mức độ

            // Bắt đầu Activity ListExercise
            startActivity(intent)
            requireActivity().finish()
        }
        // Gán sự kiện nhấn cho từng card
        view.findViewById<LinearLayout>(R.id.title2).setOnClickListener {
            // Tạo Intent để mở ListExercise
            val intent = Intent(requireContext(), ListExercise::class.java)

            // Truyền thêm dữ liệu thông qua Intent
            intent.putExtra("BoPhan", "Ngực") // Giá trị "Bung" cho bộ phận
            intent.putExtra("MucDo", "Trung Bình")   // Giá trị "De" cho mức độ

            // Bắt đầu Activity ListExercise
            startActivity(intent)
            requireActivity().finish()
        }
        // Gán sự kiện nhấn cho từng card
        view.findViewById<LinearLayout>(R.id.title3).setOnClickListener {
            // Tạo Intent để mở ListExercise
            val intent = Intent(requireContext(), ListExercise::class.java)

            // Truyền thêm dữ liệu thông qua Intent
            intent.putExtra("BoPhan", "Cánh Tay") // Giá trị "Bung" cho bộ phận
            intent.putExtra("MucDo", "Trung Bình")   // Giá trị "De" cho mức độ

            // Bắt đầu Activity ListExercise
            startActivity(intent)
            requireActivity().finish()
        }
        // Gán sự kiện nhấn cho từng card
        view.findViewById<LinearLayout>(R.id.title4).setOnClickListener {
            // Tạo Intent để mở ListExercise
            val intent = Intent(requireContext(), ListExercise::class.java)

            // Truyền thêm dữ liệu thông qua Intent
            intent.putExtra("BoPhan", "Chân") // Giá trị "Bung" cho bộ phận
            intent.putExtra("MucDo", "Trung Bình")   // Giá trị "De" cho mức độ

            // Bắt đầu Activity ListExercise
            startActivity(intent)
            requireActivity().finish()
        }
        // Gán sự kiện nhấn cho từng card
        view.findViewById<LinearLayout>(R.id.title5).setOnClickListener {
            // Tạo Intent để mở ListExercise
            val intent = Intent(requireContext(), ListExercise::class.java)

            // Truyền thêm dữ liệu thông qua Intent
            intent.putExtra("BoPhan", "Vai và Lưng") // Giá trị "Bung" cho bộ phận
            intent.putExtra("MucDo", "Trung Bình")   // Giá trị "De" cho mức độ

            // Bắt đầu Activity ListExercise
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
