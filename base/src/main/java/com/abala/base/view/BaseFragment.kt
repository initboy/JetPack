package com.abala.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.abala.base.data.BaseItemData
import com.abala.base.model.BaseModel
import com.abala.base.viewmodel.BaseViewModel

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel<out BaseModel<D>, D>, D : BaseItemData> :
    Fragment() {

    protected lateinit var mViewModel: BaseViewModel<BaseModel<D>, D>
    protected var mBinding: V? = null

    @LayoutRes
    abstract fun layoutId(): Int
    abstract fun createViewModel(): VM
    abstract fun onDataChanged(data: List<D>)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return mBinding?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel() as BaseViewModel<BaseModel<D>, D>
        lifecycle.addObserver(mViewModel)
        mViewModel.liveViewStatus.observe(this, Observer {

        })

        mViewModel.liveDataList.observe(this, Observer {
            onDataChanged(it)
        })
    }

}