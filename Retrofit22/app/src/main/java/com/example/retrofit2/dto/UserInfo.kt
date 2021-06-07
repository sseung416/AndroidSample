package com.example.retrofit2.dto

import com.google.gson.annotations.SerializedName

data class UserInfo(
//    @SerializedName("login")
    val userId: String,
    val name: String,
    val company: String
)
