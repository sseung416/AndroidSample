package com.example.dagger2test

import dagger.Component

@Component(modules = [MyModule::class])
interface MyComponent {
    fun getString(): String
}