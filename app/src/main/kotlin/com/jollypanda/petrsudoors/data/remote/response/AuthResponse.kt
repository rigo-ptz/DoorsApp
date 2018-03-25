package com.jollypanda.petrsudoors.data.remote.response

import com.google.gson.annotations.SerializedName
import com.jollypanda.petrsudoors.data.remote.entity.UserEntity

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
data class AuthResponse(
    @SerializedName("token") val accessToken: String,
    @SerializedName("user") val user: UserEntity
)