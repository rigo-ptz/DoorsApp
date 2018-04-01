package com.jollypanda.petrsudoors.ui.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.databinding.ActivityFailBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity

/**
 * @author Yamushev Igor
 * @since  01.04.18
 */
class FailActivity : BaseActivity<ActivityFailBinding>() {
    
    override val layout: Int = R.layout.activity_fail
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.view = this
        binding.failReason = intent.getStringExtra(EXTRA_FAIL_REASON)
    }
    
    companion object {
        private val EXTRA_FAIL_REASON = "extra_fail_reason"
        
        fun getStartIntent(context: Context, failReason: String) =
                Intent(context, FailActivity::class.java).apply {
                    putExtra(EXTRA_FAIL_REASON, failReason)
                }
    }
}