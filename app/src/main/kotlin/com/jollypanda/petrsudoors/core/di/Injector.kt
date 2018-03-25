package com.jollypanda.petrsudoors.core.di

import com.jollypanda.petrsudoors.core.App
import com.jollypanda.petrsudoors.model.AccountModel
import javax.inject.Inject

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
inline fun <T> inject(crossinline block: Injector.() -> T): Lazy<T> = lazy { Injector().block() }

class Injector {

    @Inject
    lateinit var accountModel: AccountModel

    init {
        App.instance.coreComponent.injectTo(this)
    }
}