package com.jollypanda.petrsudoors.data.local

import android.content.SharedPreferences
import androidx.content.edit

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class LocalStorage(private val prefs: SharedPreferences) {
    var accessToken: String?
        get() {
            return prefs.getString("sp_access_token", null)
        }
        set(value) {
            prefs.edit { putString("sp_access_token", value) }
        }
}