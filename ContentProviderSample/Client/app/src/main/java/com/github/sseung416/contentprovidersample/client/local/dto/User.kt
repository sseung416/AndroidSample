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

    @ColumnInfo("u_age")
    val age: Int,

    @ColumnInfo("u_c_id")
    val favoriteColorId: Int?,
) {
    companion object {
        const val KEY_USER_ID = "_ID"
        const val KEY_NAME = "u_name"
        const val KEY_AGE = "u_age"
        const val KEY_COLOR_ID = "u_c_id"
    }
}

fun ContentValues.toUser() =
    User(
        id = getAsInteger(User.KEY_USER_ID),
        name = getAsString(User.KEY_NAME),
        age = getAsInteger(User.KEY_AGE),
        favoriteColorId = getAsInteger(User.KEY_COLOR_ID)
    )

fun Cursor.getUser() =
    User(
        id = getInt(0),
        name = getString(1),
        age = getInt(2),
        favoriteColorId = getInt(3)
    )