package com.github.sseung416.contentprovidersample.client.local.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: T)

    @Update
    suspend fun update(value: T)

    @Delete
    suspend fun delete(value: T)
}