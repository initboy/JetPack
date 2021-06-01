package com.abala.news.views

import com.abala.base.model.IBaseModelListener
import com.abala.base.viewmodel.BaseViewModel

class NewsModel(private val listener: IBaseModelListener) {
    private var page = 1

    fun refresh() {
        page = 1
        loadNextPage()
    }

    fun loadNextPage() {
        val data = ArrayList<BaseViewModel>()
        //TODO load
        listener.onDataFetched(data)
        page++
    }
}