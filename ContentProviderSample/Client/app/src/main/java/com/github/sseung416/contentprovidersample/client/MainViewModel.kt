package com.github.sseung416.contentprovidersample.client

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.sseung416.contentprovidersample.client.local.dto.User
import com.github.sseung416.contentprovidersample.client.local.dto.getUser
import com.github.sseung416.contentprovidersample.client.local.dto.toContentValues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModelFactory(private val contentResolver: ContentResolver) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(contentResolver) as T
}

class MainViewModel(
    private val contentResolver: ContentResolver,
) : ViewModel() {

    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    fun getAllUsers() {
        // 디스패처 하드 코딩하면 안 되지만 귀찮으니 그냥 함
        viewModelScope.launch(Dispatchers.IO) {
            contentResolver.query(USER_CONTENT_URI, null, null, null, null)?.let { cursor ->
                _users.value = cursor.transformToUserList()
                cursor.close()
            }
        }
    }

    // 10대인 유저의 이름과 나이를 조회하는 메서드인데..
    fun getTeenagerUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val projection = arrayOf(User.KEY_NAME, User.KEY_AGE)
            val selection = "${User.KEY_AGE} = ?"
            val selectionArgs = arrayOf("10", "15")
            val sortOrder = "${User.KEY_AGE} DESC"

            // SELECT u_name, u_age FROM user WHERE u_age = 10 AND u_age = 15 ORDER BY u_age DESC
            contentResolver.query(USER_CONTENT_URI, projection, selection, selectionArgs, sortOrder)
                ?.let { cursor ->
                    _users.value = cursor.transformToUserList()
                    cursor.close()
                }
        }
    }

    fun insertUser(user: User) {
        contentResolver.insert(USER_CONTENT_URI, user.toContentValues())
    }

    fun updateUser(user: User) {
        contentResolver.update(getUserUri(user.id), user.toContentValues(), null, null)
    }

    fun deleteUser(id: Int) {
        contentResolver.delete(getUserUri(id), null, null)
    }

    private fun getUserUri(id: Int?) = ContentUris.withAppendedId(
        USER_CONTENT_URI,
        id?.toLong() ?: throw IllegalArgumentException("잘못된 user id 값입니다.")
    )
}

/**
 * Cursor를 List<User> 형태로 변환
 * */
fun Cursor.transformToUserList() = arrayListOf<User>().also { list ->
    while (moveToNext()) {
        val user = this.getUser()
        Log.d("transformToUserList", "user: $user")
        list.add(user)
    }
}.toList()