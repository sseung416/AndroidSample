package com.github.sseung416.contentprovidersample.client

import android.content.ContentResolver
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sseung416.contentprovidersample.client.local.dto.User
import com.github.sseung416.contentprovidersample.client.local.dto.getUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    fun getAllUsers(contentResolver: ContentResolver) {
        // 디스패처 하드 코딩하면 안 되지만 귀찮으니 그냥 함
        viewModelScope.launch(Dispatchers.IO) {
            val list = arrayListOf<User>()

            contentResolver.query(CONTENT_URI, null, null, null, null)?.let { cursor ->
                while (cursor.moveToNext()) {
                    val user = cursor.getUser()
                    list.add(user)
                    Log.e("TAG", "getAllUsers: $user")
                }
                cursor.close()
            }

            _users.value = list
        }
    }
}