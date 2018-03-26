package com.jollypanda.petrsudoors.ui.start

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.databinding.ActivityStartBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity
import com.jollypanda.petrsudoors.ui.login.LoginActivity
import com.jollypanda.petrsudoors.ui.main.MainActivity
import com.jollypanda.petrsudoors.utils.extension.viewModel
import com.tedpark.tedpermission.rx2.TedRx2Permission



/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
class StartActivity : BaseActivity<ActivityStartBinding>() {
    
    override val layout: Int = R.layout.activity_start
    
    private val vm by viewModel(StartViewModel::class)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissions()
    }
    
    private fun initPermissions() {
        TedRx2Permission.with(this)
            .setRationaleTitle(R.string.permission_title)
            .setRationaleMessage(R.string.permission_message)
            .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
            .request()
            .subscribe({ tedPermissionResult ->
                           if (tedPermissionResult.isGranted) {
                               initObservers()
                               vm.checkToken()
                           } else {
                               Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG).show()
                           }
                       }, { throwable ->
                       
                       }, {
                    
                       })
    }
    
    private fun initObservers() {
        vm.hasToken.observe {
            if (it == true)
                goToMain()
            else
                goToLogin()
        }
    }
    
    private fun goToLogin() {
        startActivity(LoginActivity.getStartIntent(this))
    }
    
    private fun goToMain() {
        startActivity(MainActivity.getStartIntent(this))
    }
    
}