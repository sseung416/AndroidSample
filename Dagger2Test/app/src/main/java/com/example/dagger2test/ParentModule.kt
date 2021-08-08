package com.example.dagger2test

import dagger.Module
import dagger.Provides

@Module
class ParentModule {
    @Provides
    fun provideName(): String = "ParentModule"
}