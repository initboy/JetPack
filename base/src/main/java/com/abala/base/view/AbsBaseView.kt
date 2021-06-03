package com.abala.base.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.abala.base.data.BaseItemData

abstract class AbsBaseView<V : ViewDataBinding, D : BaseItemData>(context: Context) :
    @JvmOverloads LinearLayout(context), IBaseView<D> {

    protected var binding: V

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun onRootViewClick(v: View?)

    protected abstract fun <D> bind(data: D)

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, layoutId(), this, false)
        binding.root.setOnClickListener { v: View? ->
            onRootViewClick(v)
        }
        addView(binding.root)
    }

    override fun <D> setItemData(itemData: D) {
        bind(itemData)
        binding.executePendingBindings()
    }

}