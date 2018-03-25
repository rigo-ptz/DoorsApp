package com.jollypanda.petrsudoors.ui.view.state

import com.jollypanda.petrsudoors.R

/**
 * @author Yamushev Igor
 * @since  25.03.18
 */
class ProgressStateHolder : StateHolder<ViewStateProgressBinding>() {
    
    override fun getLayout(): Int = R.layout.view_state_progress
    
    override fun onBind(binding: ViewStateProgressBinding) { }
    
}