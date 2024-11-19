package com.example.appluyentap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class BeginnerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beginner, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gán sự kiện nhấn cho từng card
        view.findViewById<LinearLayout>(R.id.title1).setOnClickListener {
            navigateToHomepage()
        }


        }

    private fun navigateToHomepage() {
        // Nếu bạn muốn mở một Activity mới
        val intent = Intent(requireContext(), ListExercise::class.java)
        startActivity(intent)

    }


    companion object {
        @JvmStatic
        fun newInstance() = BeginnerFragment()
    }
}
