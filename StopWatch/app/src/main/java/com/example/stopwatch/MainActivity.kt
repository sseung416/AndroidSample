package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1

    private lateinit var fab1: FloatingActionButton
    private lateinit var fab2: FloatingActionButton
    private lateinit var button: Button
    private lateinit var secTextView: TextView
    private lateinit var milTextView: TextView
    private lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab1 = findViewById(R.id.floatingActionButton)
        fab2 = findViewById(R.id.floatingActionButton2)
        button = findViewById(R.id.button)
        secTextView = findViewById(R.id.textView)
        milTextView = findViewById(R.id.textView2)
        layout = findViewById(R.id.linearLayout)

        fab1.setOnClickListener {
            reset()
        }
        fab2.setOnClickListener {
            isRunning = !isRunning

            if(isRunning) {
                start()
            } else {
                pause()
            }
        }
        button.setOnClickListener {
            recordLapTime()
        }

    }

    private fun start() {
        fab2.setImageResource(R.drawable.ic_baseline_pause_24)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            var milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        fab2.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel() //실행중인 타이머가 있다면 타이머를 취소
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        secTextView.text = "0"
        milTextView.text = "00"
        fab2.setImageResource(R.drawable.ic_baseline_play_arrow_24)

        layout.removeAllViews()
        lap = 1
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP: ${lapTime / 100}.${lapTime % 100}"

        layout.addView(textView, 0)
        lap++
    }
}