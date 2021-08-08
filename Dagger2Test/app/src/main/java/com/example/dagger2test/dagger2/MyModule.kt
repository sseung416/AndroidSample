package com.example.dagger2test

import dagger.Module
import dagger.Provides
import java.util.*
import java.util.stream.Collector
import javax.inject.Singleton

@Module(includes = [ParentModule::class])
class MyModule {
    @Provides
    fun providesAge(): Int = 100

    @Provides
    @Singleton
    fun provideObject(): CharSequence = "abc"
}