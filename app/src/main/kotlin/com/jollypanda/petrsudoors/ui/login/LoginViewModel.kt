package com.jollypanda.petrsudoors.ui.login

import android.arch.lifecycle.ViewModel
import com.jollypanda.petrsudoors.core.di.inject
import com.jollypanda.petrsudoors.ui.common.ErrorState
import com.jollypanda.petrsudoors.ui.common.ProgressState
import com.jollypanda.petrsudoors.ui.common.State
import com.jollypanda.petrsudoors.ui.common.SuccessState
import com.jollypanda.petrsudoors.utils.extension.getRawPhone
import com.jollypanda.petrsudoors.utils.extension.liveDataOf

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class LoginViewModel : ViewModel() {
    
    val state = liveDataOf(State())
    val credentials = liveDataOf(CredentialsWrapper())
    
    private val accountModel by inject { accountModel }
    
    fun authorize() {
        state.postValue(ProgressState())
        with(credentials.value!!) {
            accountModel.authUser(email!!, phoneNumber!!.getRawPhone(), pinCode!!)
                .subscribe({
                    if (!it.accessToken.isNullOrEmpty())
                        state.postValue(SuccessState(null))
                }, {
                    state.postValue(ErrorState(null))
                })
        }
        
    }
}