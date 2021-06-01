package com.abala.base.model

import com.abala.base.viewmodel.BaseViewModel

interface IBaseModelListener {
    fun onDataFetched(data: List<BaseViewModel>, vararg results: PagingResult?)
}