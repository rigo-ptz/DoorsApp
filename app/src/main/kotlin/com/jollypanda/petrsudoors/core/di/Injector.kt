package com.jollypanda.petrsudoors.core.di

import com.google.gson.Gson
import com.jollypanda.petrsudoors.core.App
import com.jollypanda.petrsudoors.data.local.LocalStorage
import com.jollypanda.petrsudoors.model.AccountModel
import javax.inject.Inject

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
inline fun <T> inject(crossinline block: Injector.() -> T): Lazy<T> = lazy { Injector.getInstance().block() }

class Injector {
    
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var accountModel: AccountModel
    
    @Inject
    lateinit var localStorage: LocalStorage

    init {
        App.instance.coreComponent.injectTo(this)
    }
    
    companion object {
        private var mInstance: Injector? = null
        
        fun getInstance(): Injector {
            if (mInstance == null) mInstance = Injector()
            return mInstance!!
        }
    }
}