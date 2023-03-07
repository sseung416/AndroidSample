package com.github.sseung416.contentprovidersample.client.local.dto

import android.content.ContentValues
import android.database.Cursor
import androidx.room.*

@Entity(
    tableName = "user",
    indices = [Index(value = arrayOf("_ID")), Index(value = arrayOf("u_c_id"), unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("_ID")
    val id: Int? = null,

    @ColumnInfo("u_name")
    val name: String,

    val age: Int,

    @ColumnInfo("u_c_id")
    val favoriteColorId: Int
)