package com.jollypanda.petrsudoors.ui.view.state

import android.databinding.ViewDataBinding

/**
 * @author Yamushev Igor
 * @since  25.03.18
 */
abstract class StateHolder <in T : ViewDataBinding> {
    abstract fun getLayout(): Int
    abstract fun  onBind(binding: T)
}