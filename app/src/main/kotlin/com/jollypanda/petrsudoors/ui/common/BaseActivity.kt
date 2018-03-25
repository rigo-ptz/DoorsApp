package com.jollypanda.petrsudoors.ui.common

import android.arch.lifecycle.LiveData
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jollypanda.petrsudoors.utils.extension.observe
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
abstract class BaseActivity <B : ViewDataBinding> : AppCompatActivity() {

    abstract val layout: Int
    lateinit var binding: B
    
    private var compositeDisposable: CompositeDisposable? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
    }
    
    override fun onStop() {
        compositeDisposable?.dispose()
        super.onStop()
    }
    
    fun <T> LiveData<T>.observe(observer: (T?) -> Unit) {
        this.observe(this@BaseActivity, observer)
    }
    
    protected fun validate(vararg validators: BehaviorSubject<Boolean>, subscriber: (Boolean) -> Unit) {
        validate(validators.asList(), subscriber)
    }
    
    protected fun validate(validators: List<BehaviorSubject<Boolean>>, subscriber: (Boolean) -> Unit) {
        compositeDisposable = CompositeDisposable()
        val disp = Observable
            .combineLatest(validators, { !it.contains(false) })
            .subscribe({ subscriber.invoke(it) })
    }
    
}