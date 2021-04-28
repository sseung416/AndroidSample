package com.example.cardviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val list = arrayListOf<itemsData>(
        itemsData("여행 가고 싶음", "내 능지 떡락이야. . ."),
        itemsData("여행 가고 싶음", "내 능지 떡락이야. . ."),
        itemsData("여행 가고 싶음", "내 능지 떡락이야. . ."),
        itemsData("여행 가고 싶음", "내 능지 떡락이야. . ."),
        itemsData("여행 가고 싶음", "내 능지 떡락이야. . .")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val adapter = CustomAdapter(list);
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }
}