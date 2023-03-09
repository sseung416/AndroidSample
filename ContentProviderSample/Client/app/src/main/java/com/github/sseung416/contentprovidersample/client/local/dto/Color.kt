package com.github.sseung416.contentprovidersample.client.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color")
data class Color(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("c_id")
    val id: Int? = null,

    @ColumnInfo("c_name")
    val name: String,

    val color: Long,
)