package com.example.dagger2test

import dagger.Module
import dagger.Provides

@Module
class MyModule {
    @Provides
    fun providesHelloWorld(): String {
        return "Hello World"
    }
}