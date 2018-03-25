package com.jollypanda.petrsudoors.ui.start

import android.os.Bundle
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.databinding.ActivityStartBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity
import com.jollypanda.petrsudoors.ui.login.LoginActivity
import com.jollypanda.petrsudoors.utils.extension.viewModel

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class StartActivity : BaseActivity<ActivityStartBinding>() {
    
    override val layout: Int = R.layout.activity_start
    
    private val vm by viewModel(StartViewModel::class)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
        vm.checkToken()
    }
    
    private fun initObservers() {
        vm.hasToken.observe {
            if (it == true)
                goToGetKey()
            else
                goToLogin()
        }
    }
    
    private fun goToLogin() {
        startActivity(LoginActivity.getStartIntent(this))
    }
    
    private fun goToGetKey() {
    
    }
    
}