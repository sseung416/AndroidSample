package com.example.mygallary

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


//PagerAdapter
//ViewPager에 표시할 내용을 정의하려면 어댑터(아이템의 목록 정보를 가진 객체) 필요
//어댑터 종류 >>
//FragmentPagerAdapter: 페이지 내용이 영구적일 때 적합. 로딩한 페이지 메모리에 보관 -> 페이지 많으면 많은 메모리 사용
//FragmentStatePagerAdapter: 많은 수의 페이지에 적합. 보이지 않는 페이지 메모리에서 제거 ㄱㄴ -> 적은 메모리!

//는 ViewPager의 내용 지금은 ViewPager2!!!!!!!!
//따라서 FragmentStateAdapter 사용

class MyPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    //뷰페이저가 표시할 프래그먼트 목록
    private val items = ArrayList<Fragment>()

    //viewPager 시절
//    //아이템 개수
//    override fun getCount(): Int {
//        return items.size
//    }
//
//    //position 위치의 프래그먼트
//    override fun getItem(position: Int): Fragment {
//        return items[position]
//    }

    //viewPager2 !!!!!!!!!!!!!
    //아이템 갱신
    fun updateFragments(items : List<Fragment>) {
        this.items.addAll(items)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}