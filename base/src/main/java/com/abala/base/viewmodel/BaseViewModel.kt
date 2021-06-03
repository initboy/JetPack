package com.abala.base.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abala.base.data.BaseItemData
import com.abala.base.model.BaseModel
import com.abala.base.model.OnFetchedListener

abstract class BaseViewModel<M : BaseModel<D>, D : BaseItemData> :
    ViewModel(), LifecycleObserver, OnFetchedListener<D> {

    protected var mModel: M? = null
    val liveDataList = MutableLiveData<List<D>>()
    val liveViewStatus = MutableLiveData<ViewStatus>()

    abstract fun createModel(): M

    fun refresh() {
        liveViewStatus.value = ViewStatus.LOADING
        ensureModelCreate()
        mModel?.refresh()
    }

    fun ensureModelCreate() {
        if (mModel == null) {
            mModel = createModel()
            mModel?.register(this)
        }
    }

}