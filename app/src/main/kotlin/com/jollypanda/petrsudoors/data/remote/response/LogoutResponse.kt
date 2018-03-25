package com.jollypanda.petrsudoors.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
data class LogoutResponse(
    @SerializedName("datail") val errorDetail: String?
)