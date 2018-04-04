package com.jollypanda.petrsudoors.ui.main

import android.arch.lifecycle.ViewModel
import com.google.android.gms.nearby.connection.Payload
import com.jollypanda.petrsudoors.core.di.inject
import com.jollypanda.petrsudoors.data.remote.response.KeyResponse

/**
 * @author Yamushev Igor
 * @since  26.03.18
 */
class MainViewModel : ViewModel() {
    
    private val accountModel by inject { accountModel }
    private val gson by inject { gson }
    
    var endPointId: String? = null
    
    enum class ACTION(val value: String) {
        GET_KEY("get_key"),
        RETURN_KEY("return_key")
    }
    
    fun getPayload(roomNum: String, action: ACTION): Payload {
        val payload = "{" +
                      "     \"action\": \"${action.value}\"," +
                      "     \"token\": \"${accountModel.getAccessToken()!!}\"," +
                      "     \"num\": $roomNum" +
                      "}"
        return Payload.fromBytes(payload.toByteArray())
    }
    
    fun handlePayload(payload: Payload,
                      onSuccessResponse: (KeyResponse) -> Unit,
                      onErrorResponse: (KeyResponse) -> Unit) {
        val s = String(payload.asBytes()!!)
        val response = gson.fromJson<KeyResponse>(s, KeyResponse::class.java)
        if (response.failReason != null)
            onErrorResponse.invoke(response)
        else
            onSuccessResponse.invoke(response)
    }
    
}