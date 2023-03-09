package com.github.sseung416.contentprovidersample.client.local.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value: T): Long

    @Update
    fun update(value: T): Int

    @Delete
    fun delete(value: T): Int
}