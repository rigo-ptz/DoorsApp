package com.jollypanda.petrsudoors.data.remote

import com.jollypanda.petrsudoors.data.remote.request.AuthRequest
import com.jollypanda.petrsudoors.data.remote.response.AuthResponse
import com.jollypanda.petrsudoors.data.remote.response.LogoutResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
interface Api {
    
    @POST("user/authorize")
    fun authUser(@Body authRequest: AuthRequest): Single<Response<AuthResponse>>
    
    @DELETE("user/logout")
    fun logoutUser(): Single<Response<LogoutResponse>>
}