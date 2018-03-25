package com.jollypanda.petrsudoors.data.remote.interceptor

import com.jollypanda.petrsudoors.core.di.inject
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        val accountModel by inject { accountModel }
        val token = accountModel.getAccessToken()
        
        if (token != null)
            requestBuilder
                .header("Authorization", "JWT $token")
                .method(original.method(), original.body())
        
        return chain.proceed(requestBuilder.build())
    }

}