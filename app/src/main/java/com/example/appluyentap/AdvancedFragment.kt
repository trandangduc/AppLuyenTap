package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

// Định nghĩa các tham số cho Fragment nếu cần
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdvancedFragment : Fragment() {

    // Các biến để nhận tham số nếu cần
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment cho Nâng Cao
        return inflater.inflate(R.layout.fragment_advanced, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gán sự kiện nhấn cho từng card
        view.findViewById<LinearLayout>(R.id.title1).setOnClickListener {
            // Tạo Intent để mở ListExercise
            val intent = Intent(requireContext(), ListExercise::class.java)

            // Truyền thêm dữ liệu thông qua Intent
            intent.putExtra("BoPhan", "Bụng") // Giá trị "Bung" cho bộ phận
            intent.putExtra("MucDo", "Khó")   // Giá trị "De" cho mức độ

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
            intent.putExtra("MucDo", "Khó")   // Giá trị "De" cho mức độ

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
            intent.putExtra("MucDo", "Khó")   // Giá trị "De" cho mức độ

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
            intent.putExtra("MucDo", "Khó")   // Giá trị "De" cho mức độ

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
            intent.putExtra("MucDo", "Khó")   // Giá trị "De" cho mức độ

            // Bắt đầu Activity ListExercise
            startActivity(intent)
            requireActivity().finish()
        }
    }
    companion object {
        // Phương thức để tạo một instance của AdvancedFragment với các tham số
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdvancedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
