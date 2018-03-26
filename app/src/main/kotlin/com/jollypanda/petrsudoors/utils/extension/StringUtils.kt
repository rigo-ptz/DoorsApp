package com.jollypanda.petrsudoors.utils.extension

/**
 * @author Yamushev Igor
 * @since  26.03.18
 */
fun String.getRawPhone(): String = if (this.isNullOrEmpty()) "" else this.replace(" ", "")
