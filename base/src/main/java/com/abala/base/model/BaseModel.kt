package com.abala.base.model

import com.abala.base.data.BaseItemData
import java.lang.ref.WeakReference

abstract class BaseModel<D : BaseItemData> : OnCompletedListener<D> {
    private var mLoading: Boolean = false
    private var mRefListener: WeakReference<OnFetchedListener<D>>? = null

    abstract fun load()

    fun refresh() {
        if (!mLoading) {
            mLoading = true
            load()
        }
    }

    fun register(listener: OnFetchedListener<D>) {
        if (mRefListener == null) {
            mRefListener = WeakReference(listener)
        }
    }

    protected fun notifyDataFetched(data: List<D>) {
        mRefListener?.get()?.onFetched(this, data)
        mLoading = false
    }

}