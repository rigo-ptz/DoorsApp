package com.jollypanda.petrsudoors.ui.main

import android.arch.lifecycle.ViewModel
import com.google.android.gms.nearby.connection.Payload
import com.jollypanda.petrsudoors.core.di.inject

/**
 * @author Yamushev Igor
 * @since  26.03.18
 */
class MainViewModel : ViewModel() {
    
    private val accountModel by inject { accountModel }
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
    
}