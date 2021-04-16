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
            intent.putExtra("height", HEditText.text.toString())
            intent.putExtra("weight", WEditText.text.toString())

            startActivity(intent)
        }
    }

    private fun saveData(height: Int, weight: Int) {
        val pref: SharedPreferences = this.getPreferences(0)
        val editor = pref.edit()

        editor
                .putInt("KEY_HEIGHT", height)
                .putInt("KEY_WEIGHT", weight)
                .apply()
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