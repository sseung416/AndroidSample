package com.example.broadcastreceivertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import java.util.*
import kotlin.collections.ArrayList

class SmsReceiver : BroadcastReceiver() {

    //SMS 받으면 onReceive() 메서드 자동 완성
    override fun onReceive(context: Context?, intent: Intent?) {
        //인텐트에서 Bundle 객체 가져오기
        val bundle = intent?.extras;
        val messages: ArrayList<SmsMessage> = parseSmsMessage(bundle)

        if(messages != null && messages.size > 0) {
            val sender: String? = messages[0].originatingAddress

            val contents: String? = messages[0].messageBody

            val receivedData: Date = Date(messages[0].timestampMillis)
        }




    }

    private fun parseSmsMessage(bundle: Bundle?) : ArrayList<SmsMessage> {
        //Bundel 객체에 있는 부가 데이터 중 pdus 가져오기
        val objs: ArrayList<Objects> = bundle?.get("pdus") as ArrayList<Objects>
        val messages: Array<SmsMessage> =

        var smsCount: Int = objs.size
        for (i in 0..smsCount) {
            //단말 OS 버전에 따라 다른 방식으로 호출
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES) {
                val format: String? = bundle.getString("format")
                messages[i] = SmsMessage.createFromPdu((byte[]) )
            }
        }
    }
}