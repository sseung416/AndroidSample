package com.github.sseung416.webappsample

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import com.github.sseung416.webappsample.NativeEvent.*
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val webViewState = viewModel.webViewState
    val uiState by viewModel.uiState.collectAsState()

    val webEventHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (NativeEvent.get(msg.what)) {
                HANDLE_SHOW_LOADING ->
                    viewModel.updateLoading(showLoading = true)
                HANDLE_HIDE_LOADING ->
                    viewModel.updateLoading(showLoading = false)
                HANDLE_SHOW_DIALOG ->
                    viewModel.updateDialog(showDialog = true, message = msg.obj as String)
            }
            super.handleMessage(msg)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        WebViewContent(
            webViewState = webViewState,
            handler = webEventHandler
        )

        val (showDialog, dialogMessage) = uiState.dialog
        DialogContent(
            onDismiss = { viewModel.updateDialog(showDialog = false) },
            showDialog = showDialog,
            message = dialogMessage
        )
    }
}

@Composable
fun WebViewContent(
    webViewState: WebViewState,
    handler: Handler
) {
    WebView(
        state = webViewState,
        onCreated = { webView ->
            with(webView) {
                settings.run {
                    javaScriptEnabled = true
                    isDebugInspectorInfoEnabled = true
                }
                addJavascriptInterface(JavascriptBridge(handler), "Native")
            }
        }
    )
}

@Composable
fun DialogContent(
    onDismiss: () -> Unit,
    showDialog: Boolean,
    message: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = { Text(text = message) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text(text = "확인")
                }
            }
        )
    }
}

