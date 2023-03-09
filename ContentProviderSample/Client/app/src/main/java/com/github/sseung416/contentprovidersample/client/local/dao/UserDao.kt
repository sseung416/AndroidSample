package com.github.sseung416.contentprovidersample.client.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import com.github.sseung416.contentprovidersample.client.local.dto.User

@Dao
interface UserDao : BaseDao<User> {

    // 데이터 조회 구문은 Cursor
    @Query("SELECT * FROM user")
    fun getUsers(): Cursor

    @Query("DELETE FROM user WHERE _ID = :id")
    fun delete(id: Long): Int

}