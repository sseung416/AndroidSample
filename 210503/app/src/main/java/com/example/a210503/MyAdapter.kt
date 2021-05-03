package com.example.a210503

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val items: ArrayList<ItemData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title: TextView = view.findViewById(R.id.textView)
        val tv_contents: TextView = view.findViewById(R.id.textView2)

        fun binding(items: ItemData){
            tv_title.text = items.title
            tv_contents.text = items.contents

            itemView.setOnClickListener {
                Log.d("SSS", "${items.title} 리스트 선택")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflated = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(inflated)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int = items.size
}

