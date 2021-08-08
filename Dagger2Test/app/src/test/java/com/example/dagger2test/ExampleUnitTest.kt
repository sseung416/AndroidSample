package com.example.dagger2test

import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testDagger() {
//        testFirstDI()
        testMemberInjector1()
        testMemberInjector2()
    }

    private fun testFirstDI() {
        val myComponent = DaggerMyComponent.create()
        println("result = ${myComponent.getAge()}")
        println("parent = ${myComponent.getParent()}")
    }

    private fun testMemberInjector1() {
        val myClass = MyClass()
        val myComponent = DaggerMyComponent.create()
        myComponent.inject(myClass)
        val str = myClass.getStr()
        println("result = $str")
    }

    private fun testMemberInjector2() {
        val myClass = MyClass()

        // component 생성
        val myComponent = DaggerMyComponent.create()

        // 제네릭이 MyClass인 inject 불러움
        val injector = myComponent.getInjector()

        // 주입
        injector.injectMembers(myClass)

        val str = myClass.getStr()
        println("result = $str")
    }
}