package com.example.dagger2test.jetpack.room.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val lastName: String,

//    필드 무시하기
//    @Ignore
//    val bitmap: Bitmap
)