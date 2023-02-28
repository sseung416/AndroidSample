package com.github.sseung416.contentprovidersample.client.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("u_id")
    val id: Int? = null,

    @ColumnInfo("u_name")
    val name: String,

    val age: Int,

    @ColumnInfo("u_c_id")
    val favoriteColorId: Int
)