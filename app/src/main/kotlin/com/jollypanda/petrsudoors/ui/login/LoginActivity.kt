package com.jollypanda.petrsudoors.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.databinding.ActivityLoginBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity
import com.jollypanda.petrsudoors.ui.common.ErrorState
import com.jollypanda.petrsudoors.ui.common.ProgressState
import com.jollypanda.petrsudoors.ui.common.SuccessState
import com.jollypanda.petrsudoors.ui.main.MainActivity
import com.jollypanda.petrsudoors.ui.view.state.ProgressStateHolder
import com.jollypanda.petrsudoors.utils.extension.viewModel
import com.jollypanda.petrsudoors.utils.formatting.FormattingUtils
import com.jollypanda.petrsudoors.utils.validation.isValidEmail
import com.jollypanda.petrsudoors.utils.validation.isValidPhone
import com.jollypanda.petrsudoors.utils.validation.isValidPinCode
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
        binding.credentials = vm.credentials.value
    }
    
    private fun initMaskingAndValidation() {
        with(binding.content!!) {
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
                is ProgressState -> {
                    binding.vState.showState(ProgressStateHolder())
                }
                is SuccessState<*> -> {
                    binding.vState.showContent()
                    goToMain()
                }
                is ErrorState<*> -> {
                    binding.vState.showContent()
                    Toast.makeText(this, getString(R.string.error_stub), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    fun authorize() {
        vm.authorize()
    }
    
    private fun goToMain() {
        startActivity(MainActivity.getStartIntent(this))
    }
    
    companion object {
        fun getStartIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}