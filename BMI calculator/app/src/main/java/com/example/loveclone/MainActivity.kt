package com.example.loveclone

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var HEditText: EditText
    lateinit var WEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HEditText = findViewById(R.id.heightEditText)
        WEditText = findViewById(R.id.weightEditText)
        val button : Button = findViewById(R.id.resultBtn)

        loadData()

        button.setOnClickListener {
            saveData(HEditText.text.toString().toInt(), WEditText.text.toString().toInt())

            val intent = Intent(this, ResultActivity::class.java)
            //Intent(출발할 액티비티, 도착할 액티비티)
            //Intent 선언 후 startActivity()를 사용해야 다음 화면으로 넘어감

            intent.putExtra("height", HEditText.text.toString())
            intent.putExtra("weight", WEditText.text.toString())
            //Intent.putExtra(Key값, 전달할 인자값)

            startActivity(intent)
        }
    }

    private fun saveData(height: Int, weight: Int) {
        val pref: SharedPreferences = this.getPreferences(0)
        val pref2: SharedPreferences = getSharedPreferences("test", MODE_PRIVATE)
        //test의 이름의 기본모드 설정
        val editor = pref.edit() //SharedPreference를 제어할 editor 선언

        editor
                .putInt("KEY_HEIGHT", height)
                .putInt("KEY_WEIGHT", weight)
                .apply() //파일에 저장
    }

    private fun loadData() {
        val pref = this.getPreferences(0)
        val height = pref.getInt("KEY_HEIGHT", 0)
        val weight = pref.getInt("KEY_WEIGHT", 0)

        if(height != 0 && weight != 0) {
            HEditText.setText(height.toString()) //
            WEditText.setText(weight.toString())
        }
    }
}