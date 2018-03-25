package com.jollypanda.petrsudoors.ui.start

import android.arch.lifecycle.ViewModel
import com.jollypanda.petrsudoors.core.di.inject
import com.jollypanda.petrsudoors.utils.extension.liveDataOf

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class StartViewModel : ViewModel() {
    
    private val accountModel by inject { accountModel }
    
    val hasToken = liveDataOf<Boolean>()
    
    fun checkToken() {
        hasToken.postValue(accountModel.getAccessToken() != null)
    }
}