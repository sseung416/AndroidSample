package com.example.a210513

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

class BlankAdapter(private val items: ArrayList<Data>) : RecyclerView.Adapter<BlankAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv: TextView = itemView.findViewById(R.id.textView)

        fun binding(item: Data) {
            tv.text = item.s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflated = LayoutInflater.from(parent.context).inflate(R.layout.items_list, parent, false)
        return Holder(inflated)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding(items[position]);
    }

    override fun getItemCount(): Int = items.size
}