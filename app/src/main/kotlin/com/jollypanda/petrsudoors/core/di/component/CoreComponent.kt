package com.jollypanda.petrsudoors.core.di.component

import com.jollypanda.petrsudoors.core.di.Injector
import com.jollypanda.petrsudoors.core.di.module.AppModule
import com.jollypanda.petrsudoors.core.di.module.DataModule
import com.jollypanda.petrsudoors.core.di.module.ModelsModule
import com.jollypanda.petrsudoors.core.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
@Singleton
@Component(
        modules = arrayOf(
                AppModule::class,
                NetworkModule::class,
                ModelsModule::class,
                DataModule::class
        )
)
interface CoreComponent {
    fun injectTo(injector: Injector)
}