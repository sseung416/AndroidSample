package com.example.dagger2test

import javax.inject.Inject

class MyClass {

    @Inject
    lateinit var string: String

    fun getStr(): String = string
}