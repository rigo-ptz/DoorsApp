package com.jollypanda.petrsudoors.ui.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.data.remote.response.ACTION
import com.jollypanda.petrsudoors.data.remote.response.KeyResponse
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
        binding.roomNumber = intent.getStringExtra(EXTRA_ROOM_NUMBER)
        binding.roomFloor = intent.getStringExtra(EXTRA_ROOM_FLOOR)
        binding.action = intent.getSerializableExtra(EXTRA_ROOM_ACTION) as ACTION
    }
    
    companion object {
        private val EXTRA_ROOM_NUMBER = "extra_room_number"
        private val EXTRA_ROOM_FLOOR = "extra_room_floor"
        private val EXTRA_ROOM_ACTION = "extra_action"
        
        fun getStartIntent(
            context: Context,
            keyResponse: KeyResponse
        ) = Intent(context, SuccessActivity::class.java).apply {
            putExtra(EXTRA_ROOM_NUMBER, keyResponse.roomNumber!!)
            putExtra(EXTRA_ROOM_FLOOR, keyResponse.roomFloor!!)
            putExtra(EXTRA_ROOM_ACTION, keyResponse.action)
        }
    }
}