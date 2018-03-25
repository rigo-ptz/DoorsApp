package com.jollypanda.petrsudoors.data.remote.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
data class UserEntity(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("last_name") val last_name: String
)