package com.jollypanda.petrsudoors.core.di.module

import com.jollypanda.petrsudoors.data.local.LocalStorage
import com.jollypanda.petrsudoors.data.remote.Api
import com.jollypanda.petrsudoors.model.AccountModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
@Module
class ModelsModule {
    
    @Provides
    @Singleton
    fun provideAccountModel(api: Api, localStorage: LocalStorage) = AccountModel(api, localStorage)
}