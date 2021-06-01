package com.abala.base.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.abala.base.viewmodel.BaseViewModel

abstract class AbsBaseView<B : ViewDataBinding, VM : BaseViewModel>(context: Context) :
    @JvmOverloads LinearLayout(context), IBaseView<VM> {

    protected var binding: B

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun onRootViewClick(v: View?)

    protected abstract fun <VM> bind(data: VM)

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, layoutId(), this, false)
        binding.root.setOnClickListener { v: View? ->
            onRootViewClick(v)
        }
        addView(binding.root)
    }

    override fun <VM> setViewModel(viewModel: VM) {
        bind(viewModel)
        binding.executePendingBindings()
    }

}