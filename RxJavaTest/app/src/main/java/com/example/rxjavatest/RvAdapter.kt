package com.example.rxjavatest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvAdapter : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    private var list: ArrayList<String> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv: TextView = itemView.findViewById(R.id.tv_rv_item)

        fun binding(s: String) {
            tv.text = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(list[position])
    }

    fun addItem(item: String) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(item: String) {
        list.remove(item)
        notifyDataSetChanged()
    }
}