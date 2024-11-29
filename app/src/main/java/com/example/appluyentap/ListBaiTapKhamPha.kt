package com.example.appluyentap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import data.ListKhamPha

class BaiTapAdapter(
    private val baiTapList: List<ListKhamPha>,
    private val onItemClick: (ListKhamPha) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = baiTapList.size

    override fun getItem(position: Int): Any = baiTapList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_list_explore, parent, false)

        val baiTap = baiTapList[position]
        val button: Button = view.findViewById(R.id.btnkhampha)

        button.text = baiTap.Ten // Hiển thị tên bài tập
        button.setOnClickListener { onItemClick(baiTap) }

        return view
    }
}
