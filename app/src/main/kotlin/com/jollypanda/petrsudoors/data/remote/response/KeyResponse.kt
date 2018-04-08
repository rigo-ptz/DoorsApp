package com.jollypanda.petrsudoors.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author Yamushev Igor
 * @since  04.04.18
 */
data class KeyResponse(
    @SerializedName("action") val action: ACTION,
    @SerializedName("number") val roomNumber: String?,
    @SerializedName("floor") val roomFloor: String?,
    @SerializedName("reason") val failReason: String?
)

enum class ACTION(val value: String) {
    @SerializedName("get_key")
    GET_KEY("get_key"),
    @SerializedName("get_key_by_schedule")
    GET_KEY_BY_SCHEDULE("get_key_by_schedule"),
    @SerializedName("return_key")
    RETURN_KEY("return_key")
}