package com.example.a210517

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val items: ArrayList<Int>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun binding(items: Int) {
            imageView.setImageResource(items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflated = LayoutInflater.from(parent.context).inflate(R.layout.activity_image_item, parent, false)
        return ViewHolder(inflated)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position])
    }

}