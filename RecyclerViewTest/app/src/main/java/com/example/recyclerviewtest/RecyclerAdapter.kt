package com.example.recyclerviewtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val items: ArrayList<Data>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title: TextView = view.findViewById(R.id.textView)
        val tv_contents: TextView = view.findViewById(R.id.textView2)

        //데이터 세팅
        fun bindItems(item: Data) {
            tv_title.text = item.title
            tv_contents.text = item.contents
        }
    }

    //새로운 ViewHolder 만들 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(inflatedView)
    }

    //ViewHolder를 데이터와 연결할 때 이 메서드 호출
    //적절한 데이터를 가져와 그 데이터를 사용해 뷰 홀더의 레이아웃 채움
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    //개수 반환
    override fun getItemCount(): Int = items.size
}