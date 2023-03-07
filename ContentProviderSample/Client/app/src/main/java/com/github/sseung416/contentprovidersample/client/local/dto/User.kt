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
    val favoriteColorId: Int,
) {
    companion object {
        const val KEY_USER_ID = "userId"
        const val KEY_NAME = "name"
        const val KEY_AGE = "age"
        const val KEY_COLOR_ID = "colorId"
    }
}

fun ContentValues.getUser() =
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