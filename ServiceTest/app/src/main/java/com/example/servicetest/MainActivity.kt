package com.example.servicetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.editText)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener() {
            val name: String = editText.text.toString()

            //인텐트 객체 만들고 extra 데어터 넣기
            val intent: Intent = Intent(this, MyService::class.java)
            intent.putExtra("command", "show")
            intent.putExtra("name", name);

            //서비스 시작 -> 인텐트 객체는 onStratCommand() 메서드로 전달됨
            startService(intent);
        }

        //액티비티가 새로 만들어질 때 전달된 인텐트 처리하기
        val passedIntent: Intent = intent
        processIntent(passedIntent)
    }

    override fun onNewIntent(intent: Intent?) {
        //액티비티가 이미 만들어져 있을 때 전달된 인텐트 처리하기

        if (intent != null) {
            processIntent(intent)
        }

        super.onNewIntent(intent)
    }

    private fun processIntent(intent: Intent) {
        //전달된 인텐트가 있다면 실행
        if(intent != null) {
            val command: String = intent.getStringExtra("command").toString()
            val name: String = intent.getStringExtra("name").toString()

            Toast.makeText(this, command + " " +name, Toast.LENGTH_LONG).show()
        }
    }
}