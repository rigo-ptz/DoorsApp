package com.jollypanda.petrsudoors.ui.main

import android.arch.lifecycle.ViewModel
import com.google.android.gms.nearby.connection.Payload
import com.jollypanda.petrsudoors.core.di.inject
import com.jollypanda.petrsudoors.data.remote.response.ACTION
import com.jollypanda.petrsudoors.data.remote.response.KeyResponse

/**
 * @author Yamushev Igor
 * @since  26.03.18
 */
class MainViewModel : ViewModel() {
    
    private val localStorage by inject { localStorage }
    private val accountModel by inject { accountModel }
    private val gson by inject { gson }
    
    var endPointId: String? = null
    
    var hasNotReturnedKey: Boolean = false
        get() {
            return !localStorage.roomNumber.isNullOrEmpty()
        }
    
    var savedRoomNumber: String?
        get() {
           return localStorage.roomNumber
        }
        set(value) {
            localStorage.roomNumber = value
        }
    
    fun getPayload(roomNum: String, action: ACTION): Payload {
        val payload = "{" +
                      "     \"action\": \"${action.value}\"," +
                      "     \"token\": \"${accountModel.getAccessToken()!!}\"," +
                      "     \"num\": $roomNum" +
                      "}"
        return Payload.fromBytes(payload.toByteArray())
    }
    
    fun getPayloadForSchedule(dateTime: String, action: ACTION): Payload {
        val payload = "{" +
                      "     \"action\": \"${action.value}\"," +
                      "     \"token\": \"${accountModel.getAccessToken()!!}\"," +
                      "     \"time\": \"$dateTime\"" +
                      "}"
        return Payload.fromBytes(payload.toByteArray())
    }
    
    fun handlePayload(payload: Payload,
                      onSuccessResponse: (KeyResponse) -> Unit,
                      onErrorResponse: (KeyResponse) -> Unit) {
        val s = String(payload.asBytes()!!)
        val response = gson.fromJson<KeyResponse>(s, KeyResponse::class.java)
        if (response.failReason != null) {
            localStorage.roomNumber = null
            onErrorResponse.invoke(response)
        } else {
            when (response.action) {
                ACTION.GET_KEY,
                ACTION.GET_KEY_BY_SCHEDULE -> {
                    localStorage.roomNumber = response.roomNumber
                }
                ACTION.RETURN_KEY -> {
                    localStorage.roomNumber = null
                }
            }
            onSuccessResponse.invoke(response)
        }
    }
    
}