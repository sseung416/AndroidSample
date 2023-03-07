package com.github.sseung416.contentprovidersample.client

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.github.sseung416.contentprovidersample.client.local.LocalDatabase
import com.github.sseung416.contentprovidersample.client.local.dto.getUser

class UserProvider : ContentProvider() {

    // Content URI 설정
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(PROVIDER_URL, PATH_USER, PATH_USER_CODE)

        addURI(PROVIDER_URL, PATH_COLOR, PATH_COLOR_CODE)
    }

    private lateinit var localDatabase: LocalDatabase

    private val userDao by lazy { localDatabase.userDao() }

    // 처음 ContentProvider가 생성될 때 실행됨, 로컬 데이터베이스 초기화
    override fun onCreate(): Boolean {
        localDatabase = LocalDatabase.getInstance(requireContext())
        return this::localDatabase.isInitialized
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor = uri.whenUserPath {
        userDao.getUsers().apply { setNotificationUri(requireContext().contentResolver, it) }
    }

    override fun getType(uri: Uri): String = "멀 넣어줘야 돼??"

    // ContentValues 값을 기반으로, 테이블에 값을 삽입해야 함
    // 새로 추가된 데이터의 Content URI를 반환해야 함
    override fun insert(uri: Uri, values: ContentValues?): Uri? = uri.whenUserPath {
        values?.run {
            val newRowId = userDao.insert(this.getUser())
            newRowId.let { ContentUris.withAppendedId(uri, it) }
        }
    }

    // 실제 저장소의 데이터를 반드시 삭제할 필요는 없음, 해당 행이 삭제되었다는 플래그만 추가 후 이후에 삭제해도 상관 없음
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int =
        uri.whenUserPath {
            val count = userDao.delete(ContentUris.parseId(uri))
            requireContext().contentResolver.notifyChange(uri, null)
            count
        }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int = uri.whenUserPath {
        values?.run {
            userDao.update(this.getUser())
        } ?: throw NullPointerException("ContentValues가 null~")
    }

    // uri가 user path와 동일 할 때 파라미터로 받은 이벤트를 실행함
    private fun <T> Uri.whenUserPath(
        event: (Uri) -> T,
    ): T = when (uriMatcher.match(this)) {
        PATH_USER_CODE -> event.invoke(this)
        else -> throw IllegalArgumentException("잘못된 uri 입니다. $this")
    }

    companion object {
        const val PROVIDER_URL = "com.github.sseung416.contentprovidersample.provider"

        private const val KEY_USER_ID = "userId"
        private const val KEY_NAME = "name"
        private const val KEY_AGE = "age"
        private const val KEY_COLOR_ID = "colorId"

        const val PATH_USER = "user"
        const val PATH_COLOR = "user/color"
        private const val PATH_USER_CODE = 1
        private const val PATH_COLOR_CODE = 2
    }
}