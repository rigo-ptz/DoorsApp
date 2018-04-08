package com.jollypanda.petrsudoors.utils.formatting

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Yamushev Igor
 * @since  08.04.18
 */
private fun Date.format(format: String): String =
        SimpleDateFormat(format, Locale.getDefault()).format(this)

fun Date.formatToRequest() = this.format("yyyy-MM-dd'T'HH:mmXXX")