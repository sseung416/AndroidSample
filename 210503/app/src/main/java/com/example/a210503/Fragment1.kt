package com.example.a210503

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fragment1 : Fragment() {

    val items: ArrayList<ItemData> = arrayListOf(
        ItemData("title1", "contents"),
        ItemData("title2", "sdfds"),
        ItemData("title3", "sdfds"),
        ItemData("title4", "sdfds"),
        ItemData("title5", "sdfds")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view!!.findViewById(R.id.recyclerView)

        val adapter = MyAdapter(items)
        recyclerView.adapter = adapter

        val layout = LinearLayoutManager(context)

        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

}