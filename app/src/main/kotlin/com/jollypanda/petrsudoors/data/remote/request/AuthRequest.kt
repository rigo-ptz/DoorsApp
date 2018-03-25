package com.jollypanda.petrsudoors.data.remote.request

import com.google.gson.annotations.SerializedName

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
data class AuthRequest(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("pin_code") val pinCode: String,
    @SerializedName("password") val password: String = "stub"
)