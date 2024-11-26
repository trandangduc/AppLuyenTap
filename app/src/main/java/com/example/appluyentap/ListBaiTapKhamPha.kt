package com.example.appluyentap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import data.ListKhamPha

class BaiTapAdapter(
    private val baiTapList: List<ListKhamPha>,
    private val onItemClick: (ListKhamPha) -> Unit
) : RecyclerView.Adapter<BaiTapAdapter.BaiTapViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaiTapViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_explore, parent, false)
        return BaiTapViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaiTapViewHolder, position: Int) {
        val baiTap = baiTapList[position]
        holder.bind(baiTap, onItemClick)
    }

    override fun getItemCount(): Int = baiTapList.size

    class BaiTapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.btnkhampha) // ID của Button trong layout

        fun bind(baiTap: ListKhamPha, onItemClick: (ListKhamPha) -> Unit) {
            button.text = baiTap.Ten // Hiển thị tên bài tập
            button.setOnClickListener { onItemClick(baiTap) }
        }
    }
}
