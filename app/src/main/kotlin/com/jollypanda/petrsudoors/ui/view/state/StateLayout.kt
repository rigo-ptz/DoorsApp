package com.jollypanda.petrsudoors.ui.view.state

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * @author Yamushev Igor
 * @since  25.03.18
 */
class StateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    
    private var contentView: View? = null

    fun <T : ViewDataBinding> showState(holder: StateHolder<T>) {
        while (childCount != 1) removeViewAt(1)
        
        contentView!!.visibility = View.GONE
    
        val binding = DataBindingUtil.inflate<T>(LayoutInflater.from(context), holder.getLayout(), this, false)
        addView(binding.root, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        holder.onBind(binding)
    }
    
    fun showContent() {
        while (childCount != 1) removeViewAt(1)
        contentView!!.visibility = View.VISIBLE
    }
    
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (contentView == null) contentView = child
        super.addView(child, index, params)
    }
}