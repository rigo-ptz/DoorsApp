package com.jollypanda.petrsudoors.utils.validation

import android.widget.TextView
import io.reactivex.subjects.BehaviorSubject

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
fun TextView.notEmpty(): BehaviorSubject<Boolean> =
        ValidationTextWatcher.addToView(this, ValidationTextWatcher.notEmpty)

fun TextView.isValidPhone(): BehaviorSubject<Boolean> =
        ValidationTextWatcher.addToView(this, ValidationTextWatcher.isValidPhone)

fun TextView.isValidEmail(): BehaviorSubject<Boolean> =
        ValidationTextWatcher.addToView(this, ValidationTextWatcher.isEmail)

fun TextView.isValidPinCode(): BehaviorSubject<Boolean> =
        ValidationTextWatcher.addToView(this, ValidationTextWatcher.isValidPinCode)