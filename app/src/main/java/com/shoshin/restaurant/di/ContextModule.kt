package com.shoshin.restaurant.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(var context: Context) {

    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}