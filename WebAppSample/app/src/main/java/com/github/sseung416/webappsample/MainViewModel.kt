package com.github.sseung416.webappsample

import androidx.lifecycle.ViewModel
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    val webViewState = WebViewState(
        WebContent.Url(url = "file:///android_asset/index.html")
    )

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun updateLoading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(loading = showLoading)
    }

    fun updateDialog(showDialog: Boolean = true, message: String = "") {
        _uiState.value = _uiState.value.copy(dialog = Pair(showDialog, message))
    }
}

data class MainUiState(
    val loading: Boolean = false,
    val dialog: Pair<Boolean, String> = Pair(false, "")
)