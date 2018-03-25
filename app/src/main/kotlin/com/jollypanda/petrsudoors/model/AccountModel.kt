package com.jollypanda.petrsudoors.model

import com.jollypanda.petrsudoors.data.local.LocalStorage
import com.jollypanda.petrsudoors.data.remote.Api
import com.jollypanda.petrsudoors.data.remote.request.AuthRequest
import com.jollypanda.petrsudoors.data.remote.response.AuthResponse
import com.jollypanda.petrsudoors.utils.extension.asRetrofitBody
import io.reactivex.Single

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class AccountModel(
        private val api: Api,
        private val localStorage: LocalStorage
) {
    fun getAccessToken() = localStorage.accessToken
    
    fun authUser(email: String, phone: String, pinCode: String): Single<AuthResponse> =
            api.authUser(AuthRequest(phone, email, pinCode))
                .doOnSuccess { if (it.isSuccessful && it.body()?.accessToken != null)
                    localStorage.accessToken = it.body()?.accessToken
                }
                .asRetrofitBody()
    
}