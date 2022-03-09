package com.shoshin.restaurant.di

import android.content.Context
import com.shoshin.data.repositories.Preferences
import com.shoshin.domain_abstract.repositories.IPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PreferencesModule {
    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): IPreferencesRepository {
        return Preferences(context)
    }
}