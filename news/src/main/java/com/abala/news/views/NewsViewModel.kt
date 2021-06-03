package com.abala.news.views


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abala.base.model.BaseModel
import com.abala.base.viewmodel.BaseViewModel
import com.abala.common.views.textnews.TextNewsItemData

class NewsViewModel : BaseViewModel<NewsModel, TextNewsItemData>() {

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsViewModel() as T
        }
    }

    override fun createModel(): NewsModel {
        return NewsModel()
    }

    override fun onFetched(model: BaseModel<TextNewsItemData>, data: List<TextNewsItemData>) {

    }
}