package com.jollypanda.petrsudoors.core.di.module

import android.content.Context
import com.jollypanda.petrsudoors.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
@Module
class DataModule {
    
    @Provides
    @Singleton
    fun provideLocalStorage(context: Context) = LocalStorage(context.getSharedPreferences("doors_SP", 0))
}