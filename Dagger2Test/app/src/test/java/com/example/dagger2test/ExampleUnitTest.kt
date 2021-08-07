package com.example.dagger2test

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testDagger() {
        val myComponent = DaggerMyComponent.create()
        println("result = ${myComponent.getString()}")
    }
}