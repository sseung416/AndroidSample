package com.example.titsenor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {

    //페인트 객체 초기화
    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()

    private var cX: Float = 0f
    private var cY: Float = 0f
    private var yCoord: Float = 0f
    private var xCoord: Float = 0f

    init {
        greenPaint.color = Color.GREEN //녹색 페인트
        blackPaint.style = Paint.Style.STROKE //검은색 테두리 페인트
        //style 프로퍼티: FILL, FILL_AND_STROKE, STROKE
    }

    //그래픽 API: Canvas, Paint
    override fun onDraw(canvas: Canvas?) {
        //drawCircle(x좌표, y좌표, 반지름, Paint 객체): 원그리기
        //drawLine(한 점의 x좌표, 한 점의 y좌표, 다른 점의 x좌표, 다른 점의 y좌표, Paint 객체)

        //바깥 원
        canvas?.drawCircle(cX, cY, 100f, blackPaint)
        //녹색 원
        canvas?.drawCircle(xCoord + cX, yCoord + cY, 100f, greenPaint)
        //가운데 십자가
        canvas?.drawLine(cX - 20f, cY, cX - 20f, cY, blackPaint)
        canvas?.drawLine(cX, cY - 20f, cX, cY - 20f, blackPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w / 2f
        cY = h / 2f
    }

    fun onSensorEvent(event: SensorEvent) {
        //화면을 가로로 돌렸으므로 X축과 Y축을 서로 바꿈
        yCoord = event.values[0] * 20
        xCoord = event.values[1] * 20

        invalidate() //onDraw() 메서드를 다시 호출함
    }
}

