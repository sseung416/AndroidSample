package com.example.tablayouttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val tabTextList = arrayListOf("xxx1", "xxx2", "xxx3")
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager2 : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)

        viewPager2.adapter = CustomFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2) { //TabLayout과 ViewPager2 연결하는 중재자
            tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }
}