package com.example.a210513

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class BlankFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BlankAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootGroup: ViewGroup = inflater.inflate(R.layout.fragment_blank, container, false) as ViewGroup
        recyclerView = rootGroup.findViewById(R.id.recyclerView)
        val items = arrayListOf<Data>(Data("123"),Data("123"),Data("123"),Data("123"),Data("123"))
        adapter = BlankAdapter(items)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

    }

}