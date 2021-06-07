package com.example.retrofit2.`object`

import com.example.retrofit2.dao.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASE_URL = "";

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
}