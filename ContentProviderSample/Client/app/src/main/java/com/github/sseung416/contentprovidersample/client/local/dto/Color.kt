package com.github.sseung416.contentprovidersample.client.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "color",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("u_c_id"),
        childColumns = arrayOf("c_id")
    )]
)
data class Color(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("c_id")
    val id: Int? = null,

    @ColumnInfo("c_name")
    val name: String,

    val color: Long,
)