package com.example.servicetest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.lang.Exception

class MyService : Service() {

    //데이터 바인딩에 사용
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "onCreate() 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy() 호출")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand() 호출")

        //인텐트 객체가 null이 아니면 processCommand 호출
        if(intent == null) {
            return START_STICKY //시스템이 비정상 종료되었다
            //-> 시스템 자동 재시작
        } else {
            processCommand(intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun processCommand(intent: Intent) {
        val command: String? = intent.getStringExtra("command")
        val name: String? = intent.getStringExtra("name")

        Log.d("MyService", "command:" + command + ", name:" + name)

        for(i in 1..5) {
            try {
                Thread.sleep(1000);
            } catch (e: Exception) { }

            Log.d("MyService", "Wating" + i + " seconds.")
        }

        //엑티비티 띄우기 위한 인텐트 객체 만들기
        val showIntent: Intent = Intent(applicationContext, MainActivity::class.java)

        //플래그 추가(flag: stack으로 액티비티가 쌓인경우 정리(흐름제어)해줘야함, flag 사용해서 흐름 제어 ㄱㄴ)
        //서비스는 화면이 없어서 화면이 있는 액티비티를 띄우려면? -> 새로운 task 생성
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //이미 MainActivity 객체가 메모리에 만들어져 있다면 재사용해라
        showIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        showIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        showIntent.putExtra("command", "show")
        showIntent.putExtra("name", name + " from service.")
        //서비스에서 액티비티로 전달하고 싶을 땐, 서비스에서 startActivity() 메서드를 사용하면 됨
        //아래 실행하면 액티비티에서 전달받은 인텐트의 부가 데이터 값 받기 ㄱㄴ
        startActivity(showIntent)

    }
}