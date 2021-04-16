package com.example.loveclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val height = intent.getStringExtra("height")?.toInt()
        val weight = intent.getStringExtra("weight")?.toInt()

        val textView : TextView = findViewById(R.id.textView)
        val imageView : ImageView = findViewById(R.id.imageView);

        val bmi = weight!! / Math.pow(height!! / 100.0, 2.0)

        textView.text = when {
            bmi >= 35 -> "고도 비만"
            bmi >= 30 -> "2단계 비만"
            bmi >= 25 -> "1단계 비만"
            bmi >= 23 -> "과체중"
            bmi >= 18.5 -> "정상"
            else -> "저체중"
        }

        when {
            bmi >= 23 ->
                imageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
            bmi >= 18.5 ->
                imageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
            else ->
                imageView.setImageResource(R.drawable.ic_baseline_mood_bad_24)
        }

        Toast.makeText(this, "$bmi", Toast.LENGTH_SHORT).show()
    }
}