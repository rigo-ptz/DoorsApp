package com.jollypanda.petrsudoors.ui.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.databinding.ActivitySuccessBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity

/**
 * @author Yamushev Igor
 * @since  01.04.18
 */
class SuccessActivity : BaseActivity<ActivitySuccessBinding>() {
    
    override val layout: Int = R.layout.activity_success
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.view = this
    }
    
    companion object {
        fun getStartIntent(context: Context) = Intent(context, SuccessActivity::class.java)
    }
}