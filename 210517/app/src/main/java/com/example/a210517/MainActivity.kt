package com.example.a210517

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val items = arrayListOf<Int>(R.drawable.img1, R.drawable.img3, R.drawable.img4, R.drawable.img5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val adapter = RecyclerViewAdapter(items)
        recyclerView.adapter = adapter

        val layout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layout

        //PagerSnapHelper 추가 : ViewPager 느낌 넣기
        val snapHelper: PagerSnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        //Indicator 추가
        recyclerView.addItemDecoration(ItemDecoration())

        val listener = SnapPagerScrollListener(
            PagerSnapHelper(),
            SnapPagerScrollListener.ON_SCROLL,
            true,
            object : SnapPagerScrollListener.OnChangeListener {
                override fun onSnapped(position: Int) {
                    //position 받아서 이벤트 처리
                }
            }
        )
        recyclerView.addOnScrollListener(listener)

        recyclerView.setHasFixedSize(true)
    }
}