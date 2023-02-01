package com.github.sseung416.webappsample

import android.os.Handler
import android.os.Message
import android.webkit.JavascriptInterface

class JavascriptBridge(private val eventHandler: Handler) {

    @JavascriptInterface
    fun execute(command: Int, param: String?) {
        if (param != null) {
            eventHandler.sendMessage(Message.obtain(eventHandler, command, param))
        } else {
            eventHandler.sendEmptyMessage(command)
        }
    }
}