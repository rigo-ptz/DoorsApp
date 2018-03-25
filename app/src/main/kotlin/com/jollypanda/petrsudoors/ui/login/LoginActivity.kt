package com.jollypanda.petrsudoors.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.databinding.ActivityLoginBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity
import com.jollypanda.petrsudoors.ui.common.ProgressState
import com.jollypanda.petrsudoors.utils.extension.viewModel
import com.jollypanda.petrsudoors.utils.formatting.FormattingUtils
import com.jollypanda.petrsudoors.utils.validation.isValidEmail
import com.jollypanda.petrsudoors.utils.validation.isValidPhone
import com.jollypanda.petrsudoors.utils.validation.isValidPinCode
import kotlinx.android.synthetic.main.activity_login.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    
    override val layout: Int = R.layout.activity_login
    private val vm by viewModel(LoginViewModel::class)
    
    private var phoneMaskWatcher = MaskFormatWatcher(MaskImpl.createTerminated(FormattingUtils.PHONE_SLOTS))
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMaskingAndValidation()
        initObservers()
        binding.view = this
    }
    
    private fun initMaskingAndValidation() {
        with(binding) {
            phoneMaskWatcher.installOnAndFill(etPhone)
            
            validate(etEmail.isValidEmail(),
                     etPhone.isValidPhone(),
                     etPinCode.isValidPinCode()) { valid ->
                with(btnAuthorize){
                    isEnabled = valid
                    isClickable = valid
                }
            }
        }
    }
    
    private fun initObservers() {
        vm.credentials.observe {
            binding.credentials = it
        }
        vm.state.observe {
            when (it) {
                ProgressState -> {
                
                }
                
            }
        }
    }
    
    fun authorize() {
        vm.authorize()
    }
    
    companion object {
        fun getStartIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}