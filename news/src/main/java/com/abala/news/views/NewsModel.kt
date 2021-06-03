package com.abala.news.views

import com.abala.base.model.BaseModel
import com.abala.common.views.textnews.TextNewsItemData

class NewsModel : BaseModel<TextNewsItemData>() {

    override fun load() {
        val data: List<TextNewsItemData> = ArrayList()
        onCompleted(data)
    }

    override fun onCompleted(data: List<TextNewsItemData>) {
        notifyDataFetched(data)
    }
}