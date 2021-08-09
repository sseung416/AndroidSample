package com.example.dagger2test.dagger2

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ParentModule::class])
class MyModule {
    @Provides
    fun providesAge(): Int = 100

    @Provides
    @Singleton
    fun provideObject(): CharSequence = "abc"
}