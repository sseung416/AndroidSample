package com.example.retrofit2.dao

import com.example.retrofit2.dto.UserInfo
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("users/tmddusqls") //baseUrl + values
    fun getUserInfo(): Call<UserInfo>
}