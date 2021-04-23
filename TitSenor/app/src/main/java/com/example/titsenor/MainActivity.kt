package com.example.titsenor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tiltView: TiltView

    //SensorManager 클래스의 인스턴스를 만듦
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //화면 가로 모드로 고정
        //생성자 호출 전에 설정하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        //화면 안 꺼지게 하기
        //window.addFlags(): 액티비티 상태를 지정하는 플래그 설정
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        super.onCreate(savedInstanceState)

        tiltView = TiltView(this)
        setContentView(tiltView) //R.layout.activity_main 대신 tiltView 전달
    }

    override fun onResume() {
        super.onResume()
        //registerListener(센서 값 받을 SensorEventListener, 사용할 센서 종류, 센서값을 얼마나 자주 받을지 지정)
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    //액티비티가 동작 중일 때만 센서 사용
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this) //센서 정지
    }

    //센서값이 변경되면 호출
    override fun onSensorChanged(event: SensorEvent?) {
        //values[0]: x축 값, 위로 기울이면 -10~0, 아래로 기울이면 0~10
        //values[1]: y축 값, 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
        //values[2]: z축 값, 미사용

        event?.let {
            //Log.d(태그, 메시지): 디버그용 로그 표시(로그캣에 출력)
            Log.d("MainActivity", "onSensorChanged: x:" +
                    " ${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}")

            tiltView.onSensorEvent(event)
        }
    }

    //센서 정밀도가 변경되면 호출
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }



}