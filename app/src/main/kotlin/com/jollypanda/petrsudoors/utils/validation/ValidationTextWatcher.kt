package com.jollypanda.petrsudoors.utils.validation

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.TextView
import io.reactivex.subjects.BehaviorSubject

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class ValidationTextWatcher(private val subject: BehaviorSubject<Boolean>,
                            private val validator: (String) -> Boolean) : TextWatcher {
    
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
    
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
    
    override fun afterTextChanged(editable: Editable) {
        subject.onNext(validator.invoke(editable.toString()))
    }
    
    companion object {
        fun addToView(view: TextView, validator: (String) -> Boolean): BehaviorSubject<Boolean> {
            val subj = BehaviorSubject.createDefault(false)
            view.addTextChangedListener(ValidationTextWatcher(subj, validator))
            return subj
        }
        
        val notEmpty = { s: String -> s.isNotEmpty() }
        val isValidPhone = { s: String -> s.replace(" ", "").length == 12 && s[0] == '+' }
        val isEmail = { s: String -> Patterns.EMAIL_ADDRESS.matcher(s).matches() }
        val isValidPinCode = { s: String -> s.length == 4 }
    }
    
}