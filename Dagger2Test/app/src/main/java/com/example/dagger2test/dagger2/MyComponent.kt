package com.example.dagger2test

import com.example.dagger2test.dagger2.MyClass
import dagger.Component
import dagger.MembersInjector

@Component(modules = [MyModule::class])
interface MyComponent {
    fun getAge(): Int

    fun getParent(): String

    /* Member-injection methods */
    fun inject(myClass: MyClass)
    fun getInjector(): MembersInjector<MyClass>

}