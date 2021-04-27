package com.example.fragmenttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    //프래그먼트 추가, 제거, 교체 하려면 FragmentTransaction에서 가져온 API를 사용해야 함
//    val fragmentManager = supportFragmentManager
//    val fragmentTransaction = fragmentManager.beginTransaction()
//    val fragment = TestFragment()

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button1)

        button.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, TestFragment2())
                .commit()
        }
    }
}