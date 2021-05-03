package com.example.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val dataList = arrayListOf<Data>(
        Data("아씨발좆같아요!", "욕 줄이기는 멀음!!"),
        Data("아씨발좆같아요!!", "욕 줄이기는 멀음!!!!"),
        Data("아씨발좆같아요!!!", "욕 줄이기는 멀음!!!"),
        Data("연어연어연어연어", "욕 줄이기는 멀음!!!!")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val myAdapter = RecyclerAdapter(dataList)
        recyclerView.adapter = myAdapter

        val layout = LinearLayoutManager(this)

        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)
    }
}