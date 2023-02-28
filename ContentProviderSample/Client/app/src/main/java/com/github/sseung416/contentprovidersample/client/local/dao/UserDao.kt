package com.github.sseung416.contentprovidersample.client.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.github.sseung416.contentprovidersample.client.local.dto.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM user")
    fun getUsers(): Flow<User>
}